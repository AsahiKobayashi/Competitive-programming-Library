public record P(long fi , long se) { }

class LazySegmentTree_Pair {

    final P [] Dat;
    final long[] Laz;
    final int N;
    final long E0;
    final long E1;
    final BinaryOperator<P> F;
    final LongBinaryOperator G;
    final LongBinaryOperator H;
    final LongBinaryOperator P;
    final int[] Stack = new int[64];

    LazySegmentTree_Pair(int n, long e0, long e1, BinaryOperator<P> f, LongBinaryOperator g, LongBinaryOperator h, LongBinaryOperator p) {
        this.E0 = e0;
        this.E1 = e1;
        this.F = f; this.G = g; this.H = h; this.P = p;
        int k = 1;
        while (k <= n) k <<= 1;
        this.Dat = new P[k << 1];
        this.Laz = new long[k << 1];
        this.N = k;
        for(int i = 0 ; i < Dat.length ; i ++) Dat[i] = new P(E0 , 0);
        Arrays.fill(Laz, E1);
    }
    
    public LazySegmentTree_Pair(P [] src, long e0, long e1, BinaryOperator<P> f, LongBinaryOperator g, LongBinaryOperator h, LongBinaryOperator p) {
        this(src.length, e0, e1, f, g, h, p);
        build(src);
    }

    private void build(P [] src) {
        System.arraycopy(src, 0, Dat, N, src.length);
        for (int i = N - 1; i > 0; i--) Dat[i] = F.apply(Dat[i << 1 | 0], Dat[i << 1 | 1]);
    }

    public void update(int l, int r, P v) {
        if (l >= r) return;
        int m = updown(l, r);
        l += N; r += N;
        for (; l < r; l >>= 1, r >>= 1) {
            if ((l & 1) != 0) {Laz[l] = H.applyAsLong(Laz[l], v.fi); l++;}
            if ((r & 1) != 0) {r--; Laz[r] = H.applyAsLong(Laz[r], v.fi); }
        }
        for (int i = 0; i < m; i++) {
            int k = Stack[i];
            Dat[k] = F.apply(calcDat(k << 1 | 0) , calcDat(k << 1 | 1));
        }
    }

    public P query(int i) {
        int k = 1;
        int l = 0, r = N;
        while (k < N) {
            int kl = k << 1 | 0;
            int kr = k << 1 | 1;
            Dat[k] = F.apply(calcDat(kl), calcDat(kr));
            int m = (l + r) >> 1;
            if (m > i) {r = m; k = kl;} 
            else {l = m; k = kr;}
        }
        return Dat[k];
    }

    public P query(int l, int r) {
        if (l >= r) return new P(E0 , 0);
        updown(l, r);
        P resL = new P(E0 , 0) , resR = new P(E0 , 0);
        for (l += N, r += N; l < r; l >>= 1, r >>= 1) {
            if ((l & 1) != 0) resL = F.apply(resL, calcDat(l++));
            if ((r & 1) != 0) resR = F.apply(calcDat(--r), resR);
        }
        return F.apply(resL, resR);
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

    private P calcDat(int k) {
        long lz = Laz[k];
        if (lz != E1) {
            int w = N / Integer.highestOneBit(k);
            Dat[k] = new P(G.applyAsLong(Dat[k].fi , P.applyAsLong(lz, w)) , Dat[k].se);
            if (k < N) {
                int l = k << 1 | 0, r = k << 1 | 1;
                Laz[l] = H.applyAsLong(Laz[l], lz);
                Laz[r] = H.applyAsLong(Laz[r], lz);
            }
            Laz[k] = E1;
        }
        return Dat[k];
    }

    public void debug(){
        for(int i = Dat.length - N ; i < Dat.length - 1; i ++ ) {
            System.out.print(Dat[i]+" ");
        }
        System.out.println();
    }

}
