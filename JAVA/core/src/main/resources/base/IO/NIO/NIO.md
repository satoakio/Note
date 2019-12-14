## NIO(同步非阻塞的I/O)

在Java 1.4 引入了java.nio 包，提供了 Channel , Selector，Buffer等抽象类和接口。NIO中的N可以理解为Non-blocking，不单纯是New。它支持面向缓冲的，基于通道(Channel)的I/O操作方法。 

NIO（Non-blocking I/O，在Java领域，也称为New I/O），是一种同步非阻塞的I/O模型，也是I/O多路复用的基础，已经被越来越多地应用到大型应用服务器，成为解决高并发与大量连接、I/O处理问题的有效方式。

NIO提供了与传统BIO模型中的 `Socket` 和 `ServerSocket` 相对应的 `SocketChannel` 和 `ServerSocketChannel` 两种套接字通道实现,`两种通道都支持阻塞和非阻塞两种模式。`

# NIO核心组件

NIO 包含下面几个核心的组件：
 
- Channel(通道)
- Buffer(缓冲区)
- Selector(选择器)

整个NIO体系包含的类远远不止这三个，只能说这三个是NIO体系的“核心API”.

## NIO与IO区别

>1 Channels and Buffers（通道和缓冲区）
 
- 标准的IO编程接口是面向字节流和字符流的。而NIO是面向通道和缓冲区的，数据总是从通道中读到buffer缓冲区内，或者从buffer缓冲区写入到通道中；（ NIO中的所有I/O操作都是通过一个通道开始的。）
- Java IO面向流意味着每次从流中读一个或多个字节，直至读取所有字节，它们没有被缓存在任何地方；
- Java NIO是面向缓冲区的I/O方法。 将数据读入缓冲区，使用通道进一步处理数据。 在NIO中，使用通道和缓冲区来处理I/O操作。
- 在NIO厍中，所有数据都是用缓冲区处理的。在读取数据时，它是直接读到缓冲区中的; 在写入数据时，写入到缓冲区中。任何时候访问NIO中的数据，都是通过缓冲区进行操作。
- 最常用的缓冲区是 ByteBuffer,一个 ByteBuffer 提供了一组功能用于操作 byte 数组。事实上，每一种Java基本类型（除了Boolean类型）都对应有一种缓冲区。
- 通道是双向的，可读也可写(通道总是基于缓冲区Buffer来读写)，而流的读写是单向的(只能读或者写)。无论读写，通道只能和Buffer交互。因为 Buffer，通道可以异步地读写。

>2 Non-blocking IO（非阻塞IO）
 
- Java NIO使我们可以进行非阻塞IO操作。
- Java IO的各种流是阻塞的: 意味着，当一个线程调用 `read()` 或  `write()` 时，该线程被阻塞，直到有数据可以读取，或数据完全写入。该线程在此期间不能再干任何事情了

>3 Selectors（选择器）-NIO有选择器，而IO没有。
 
- Java NIO提供了“选择器”的概念,选择器用于使用单个线程处理多个通道的数据。因此，它需要较少的线程来处理这些通道。
- 如果应用程序有多个通道(连接)打开，但每个通道(连接)的流量都很低，则可考虑使用它。 例如：在聊天服务器中。
- 线程之间的切换对于操作系统来说是昂贵的。 因此，为了提高系统效率选择器是有用的。
- 要使用Selector的话，我们必须把Channel注册到Selector上，然后就可以调用Selector的select()方法。这个方法会进入阻塞，直到有一个channel的状态符合条件。当方法返回后，线程可以处理这些事件。

![一个单线程中Slector维护3个Channel的示意图](../../pics/Selector.png)

## NIO 读数据和写数据方式

NIO中的所有IO都是从 Channel（通道） 开始的。

- 从通道进行数据读取 ：创建一个缓冲区Buffer，然后请求通道读取数据。
- 从通道进行数据写入 ：创建一个缓冲区，填充数据，并要求通道写入数据。

## 为什么大家都不愿意用 JDK 原生 NIO 进行开发呢？

不太好用，除了编程复杂、编程模型难之外，它还有以下让人诟病的问题：

- JDK 的 NIO 底层由 epoll 实现，该实现饱受诟病的空轮询 bug 会导致 cpu 飙升 100%
- 项目庞大之后，自行实现的 NIO 很容易出现各类 bug，维护成本较高。

>Netty 的出现很大程度上改善了 JDK 原生 NIO 所存在的一些让人难以忍受的问题。