interface MonoidAction<S,F> {
    /**
     * e:単位元
     * id:作用素の単位元
     * op:二項演算
     * mapping:モノイドに対し作用(l->r)
     * composition:作用の合成(l->r)
     */
    public S e ();
    public F id ();
    public S op (S l,S r);
    public S mapping(F l,S r);
    public F composition(F l,F r);
}