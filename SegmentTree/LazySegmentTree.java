class LazySegmentTree {
    final long[] Dat;
    final long[] Laz;
    final int N;
    final long E0;
    final long E1;
    final LongBinaryOperator F;
    final LongBinaryOperator G;
    final LongBinaryOperator H;
    final LongBinaryOperator P;
    final int[] Stack = new int[64];
    //初期配列 , dat(関与しない値) , lazy(関与しない値) , dat() , dat&lazy() , lazy() , lazy*len()
    public LazySegmentTree(int n, long e0, long e1, LongBinaryOperator f, LongBinaryOperator g, LongBinaryOperator h, LongBinaryOperator p) {
        this.E0 = e0;
        this.E1 = e1;
        this.F = f; this.G = g; this.H = h; this.P = p;
        int k = 1;
        while (k <= n) k <<= 1;
        this.Dat = new long[k << 1];
        this.Laz = new long[k << 1];
        this.N = k;
        Arrays.fill(Dat, E0);
        Arrays.fill(Laz, E1);
    }
    public LazySegmentTree(long[] src, long e0, long e1, LongBinaryOperator f, LongBinaryOperator g, LongBinaryOperator h, LongBinaryOperator p) {
        this(src.length, e0, e1, f, g, h, p);
        build(src);
    }
    private void build(long[] src) {
        System.arraycopy(src, 0, Dat, N, src.length);
        for (int i = N - 1; i > 0; i--) Dat[i] = F.applyAsLong(Dat[i << 1 | 0], Dat[i << 1 | 1]);
    }
    public void update(int l, int r, long v) {
        if (l >= r) return;
        int m = updown(l, r);
        l += N; r += N;
        for (; l < r; l >>= 1, r >>= 1) {
            if ((l & 1) != 0) {Laz[l] = H.applyAsLong(Laz[l], v); l++;}
            if ((r & 1) != 0) {r--; Laz[r] = H.applyAsLong(Laz[r], v);}
        }
        for (int i = 0; i < m; i++) {
            int k = Stack[i];
            Dat[k] = F.applyAsLong(calcDat(k << 1 | 0), calcDat(k << 1 | 1));
        }
    }
    public long query(int i) {
        int k = 1;
        int l = 0, r = N;
        while (k < N) {
            int kl = k << 1 | 0;
            int kr = k << 1 | 1;
            Dat[k] = F.applyAsLong(calcDat(kl), calcDat(kr));
            int m = (l + r) >> 1;
            if (m > i) {r = m; k = kl;} 
            else {l = m; k = kr;}
        }
        return Dat[k];
    }
    public long query(int l, int r) {
        if (l >= r) return E0;
        updown(l, r);
        long resL = E0, resR = E0;
        for (l += N, r += N; l < r; l >>= 1, r >>= 1) {
            if ((l & 1) != 0) resL = F.applyAsLong(resL, calcDat(l++));
            if ((r & 1) != 0) resR = F.applyAsLong(calcDat(--r), resR);
        }
        return F.applyAsLong(resL, resR);
    }
    private int updown(int l, int r) {
        if (l >= r) return 0;
        int i = 0;
        int kl = l + N, kr = r + N;
        for (int x = kl / (kl & -kl) >> 1, y = kr / (kr & -kr) >> 1; 0 < kl && kl < kr; kl >>= 1, kr >>= 1) {
            if (kl <= x) Stack[i++] = kl;
            if (kr <= y) Stack[i++] = kr;
        }
        for (; kl > 0; kl >>= 1) Stack[i++] = kl;
        int m = i;
        while (i > 0) calcDat(Stack[--i]);
        return m;
    }
    private long calcDat(int k) {
        long lz = Laz[k];
        if (lz != E1) {
            int w = N / Integer.highestOneBit(k);
            Dat[k] = G.applyAsLong(Dat[k], P.applyAsLong(lz, w));
            if (k < N) {
                int l = k << 1 | 0, r = k << 1 | 1;
                Laz[l] = H.applyAsLong(Laz[l], lz);
                Laz[r] = H.applyAsLong(Laz[r], lz);
            }
            Laz[k] = E1;
        }
        return Dat[k];
    }
    void debug()
    {
        for(int i = Dat.length - N ; i < Dat.length - 1; i ++ ) 
        {
            System.out.print(Dat[i]+" ");
        }
        System.out.println();
    }
}