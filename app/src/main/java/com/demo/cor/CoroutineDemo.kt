package com.demo.cor

class CoroutineDemo {
    /**
     * 方案 1: 取消之前的任务
     * 对于排序和过滤的情况，新请求进来，取消上一个，这样的方案是很适合的。
     * 原理：通过成员变量 activeTask 来保持对当前请求的追踪。无论何时开始一个新的排序，
     * 都立即对当前 activeTask 中的所有任务执行 cancelAndJoin 操作。这样会在开启一次新的请求之前就会把正在进行中的排序任务给取消掉。
     * 注意: 这个模式不适合在全局单例中使用，因为不相关的调用方是不应该相互取消。
     */
    var controlledRunner = ControlledRunner<List<String>>()

    suspend fun loadSortedProducts1(ascending: Boolean): List<String> {
        // 在开启新的排序之前，先取消上一个排序任务
        return controlledRunner.cancelPreviousThenRun {
            if (ascending) {
                mutableListOf("伪代码1")
            } else {
                mutableListOf("伪代码2")
            }
        }
    }

    /**
     * 方案 2: 让下一个任务排队等待
     * 让任务去排队等待依次执行，这样同一时间就只会有一个任务会被处理。就像在商场里进行排队，请求将会按照它们排队的顺序来依次处理。
     * 注意：这个方法对于排序或者是过滤来说并不是一个很好的解决方案，但是它对于解决网络请求引起的并发问题非常适合。
     * 解决：使用互斥锁
     */
    /**
     * 使用一个 SingleRunner 实例来确保同时只会有一个排序任务在进行。
     * 它使用了 Mutex，可以把它理解为一张单程票 (或是锁)，协程在必须要获取锁才能进入代码块。
     * 如果一个协程在运行时，另一个协程尝试进入该代码块就必须挂起自己，直到所有的持有 Mutex 的协程完成任务，并释放 Mutex 后才能进入。
     * 原理：使用互斥锁。如果一个协程在执行时，另一个协程执行必须先挂起自己。直到持有锁的协程执行完毕释放锁，它才能去获取锁，执行。
     */
    val singleRunner = SingleRunner()

    suspend fun loadSortedProducts2(ascending: Boolean): List<String> {
        // 开始新的任务之前，等待之前的排序任务完成
        return singleRunner.afterPrevious {
            if (ascending) {
                mutableListOf("伪代码1")
            } else {
                mutableListOf("伪代码2")
            }
        }
    }
    /**
     * 方案 3: 复用前一个任务
     * 也就是说新的请求可以重复使用之前存在的任务，比如前一个任务已经完成了一半进来了一个新的请求，
     * 那么这个请求直接重用这个已经完成了一半的任务，就省事很多。
     * 解释：但其实这种方法对于排序来说并没有多大意义，但是如果是一个网络数据请求的话，就很适用了。
     * 对于我们的库存应用来说，用户需要一种方式来从服务器获取最新的商品库存数据。
     * 我们提供了一个刷新按钮这样的简单操作来让用户点击一次就可以发起一次新的网络请求。
     * 当请求正在进行时，禁用按钮就可以简单地解决问题。但是如果我们不想这样，或者说不能这样，我们就可以选择这种方法复用已经存在的请求。
     * 原理：通过成员变量 activeTask 来保持对当前请求的追踪。无论何时开启新的请求，判断activeTask不为空，就调用它的await方法，并return
     *
     */
    suspend fun fetchProductsFromBackend(): List<String> {
        // 如果已经有一个正在运行的请求，那么就返回它。如果没有的话，开启一个新的请求。
        return controlledRunner.joinPreviousOrRun {
            val result= mutableListOf<String>()
            result
        }
    }
    /**
     * joinPreviousOrRun它会直接使用之前的请求而放弃新的请求；
     * 而 cancelPreviousAndRun 则会放弃之前的请求而创建一个新的请求。如果
     */

}