/*
 * Copyright (c) 2021. Kwai, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by Qiushi Xue <xueqiushi@kuaishou.com> on 2021.
 *
 */

#ifndef KOOM_HPROF_STRIP_H
#define KOOM_HPROF_STRIP_H

#include <android-base/macros.h>

#include <memory>
#include <string>

namespace kwai {
    namespace leak_monitor {

        class HprofStrip {
        public:
            static HprofStrip &GetInstance();

            static void HookInit();

            int HookOpenInternal(const char *path_name, int flags, ...);

            ssize_t HookWriteInternal(int fd, const void *buf, ssize_t count);

            //是否hook成功
            bool IsHookSuccess() const;

            //设置hprof文件名字
            void SetHprofName(const char *hprof_name);

        private:
            HprofStrip();

            ~HprofStrip() = default;
            //https://blog.csdn.net/u011157036/article/details/45247965
            //有时候，进行类体设计时，会发现某个类的对象是独一无二的，没有完全相同的对象，也就是对该类对象做副本没有任何意义．
            //因此，需要限制编译器自动生动的拷贝构造函数和赋值构造函数．一般参用下面的宏定义的方式进行限制，代码如下：
            DISALLOW_COPY_AND_ASSIGN(HprofStrip);

            static int GetShortFromBytes(const unsigned char *buf, int index);

            static int GetIntFromBytes(const unsigned char *buf, int index);

            static int GetByteSizeFromType(unsigned char basic_type);

            int ProcessHeap(const void *buf, int first_index, int max_len,
                            int heap_serial_no, int array_serial_no);

            static size_t FullyWrite(int fd, const void *buf, ssize_t count);

            void reset();

            //文件描述符
            int hprof_fd_;
            //裁剪的字节数
            int strip_bytes_sum_;

            //我们关注的Record类型主要是HEAP DUMP，其中又分五个子类，分别为GC ROOT、CLASS DUMP、INSTANCE DUMP、OBJECT ARRAY DUMP、PRIMITIVE ARRAY DUMP。
            //HPROF_TAG_HEAP_DUMP HPROF_TAG_HEAP_DUMP_SEGMENT 的个数
            int heap_serial_num_;
            //hook的write调用了多少次
            int hook_write_serial_num_;
            //裁剪次数计数，和strip_index_list_pair_数组结合使用
            int strip_index_;

            //是否hook成功
            bool is_hook_success_;
            //是否是系统heap，heap_type == HPROF_HEAP_ZYGOTE || heap_type == HPROF_HEAP_IMAGE，这俩需要裁剪
            bool is_current_system_heap_;

            //hprof名字
            std::string hprof_name_;

            //Strip裁剪区域数组大小 2^16 * 2 * 2 + 2
            static constexpr int kStripListLength = 65536 * 2 * 2 + 2;
            //每两个为一组，第一个值为开始位置，第二个值为结束的位置，记录裁剪区域
            int strip_index_list_pair_[kStripListLength];
        };

    }  // namespace leak_monitor
}  // namespace kwai

#endif  // KOOM_HPROF_STRIP_H