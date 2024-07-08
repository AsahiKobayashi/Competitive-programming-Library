interface Monoid<T> {
    /**
     * e:単位元
     * op:二項演算
     */
    T e();
    T op(T a, T b);
}