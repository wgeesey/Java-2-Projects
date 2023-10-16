// Dispatcher.java
// W. Geesey
// 10/5/2023
// A generic interface for dispatching messages.

package edu.fscj.cop2805c.dispatch;

public interface Dispatcher<T> {
    public void dispatch(T t);
}