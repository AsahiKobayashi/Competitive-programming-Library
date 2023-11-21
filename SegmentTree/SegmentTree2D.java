/* https://nyaannyaan.github.io/library/data-structure-2d/2d-segment-tree.hpp
* Nyaanさんのcppコードをjavaにポーティングさせていただきました.
*/
class SegmentTree2D <T> {

    private int H, W;
    private T [] dat;
    private BinaryOperator<T> op ;
    private T e ;

    SegmentTree2D(int h, int w, T e , BinaryOperator<T> op) {
        this.op = op;
        this.e = e;
        init(h, w);
    }

    private void init(int h, int w) {
        H = W = 1;
        while (H < h) H <<= 1;
        while (W < w) W <<= 1;
        dat = (T []) new Object[4 * H * W];
        Arrays.fill(dat, e);
    }

    private int id(int h, int w) {
        return h * 2 * W + w;
    }

    public void set(int h, int w, T x) {
        dat[id(h + H, w + W)] = x;
    }
    
    @SuppressWarnings("unchecked")
    public void build() {
        for (int w = W; w < 2 * W; w++) {
            for (int h = H - 1; h > 0; h--) {
                dat[id(h, w)] = op.apply(dat[id(2 * h, w)], dat[id(2 * h + 1, w)]);
            }
        }
        for (int h = 0; h < 2 * H; h++) {
            for (int w = W - 1; w > 0; w--) {
                dat[id(h, w)] = op.apply(dat[id(h, 2 * w)], dat[id(h, 2 * w + 1)]);
            }
        }
    }
    @SuppressWarnings("unchecked")
    public T get(int h, int w) {
        return dat[id(h + H, w + W)];
    }
    @SuppressWarnings("unchecked")
    public void update(int h, int w, T x) {
        h += H;
        w += W;
        dat[id(h, w)] = x;
        for (int i = h >> 1; i > 0; i >>= 1) {
            dat[id(i, w)] = op.apply(dat[id(2 * i, w)] , dat[id(2 * i + 1, w)]);
        }
        for (; h > 0; h >>= 1) {
            for (int j = w >> 1; j > 0; j >>= 1) {
                dat[id(h, j)] = op.apply(dat[id(h, 2 * j)], dat[id(h, 2 * j + 1)]);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private T _innerQuery(int h, int w1, int w2) {
        T res = e ;
        for (; w1 < w2; w1 >>= 1, w2 >>= 1) {
            if ((w1 & 1) != 0) {
                res = op.apply(res, dat[id(h, w1)]);
                w1++;
            }
            if ((w2 & 1) != 0) {
                w2--;
                res = op.apply(res, dat[id(h, w2)]);
            }
        }
        return res;
    }

    public T query(int h1, int w1, int h2, int w2) {
        if (h1 >= h2 || w1 >= w2) return e ;
        T res = e ;
        h1 += H;
        h2 += H;
        w1 += W;
        w2 += W;
        for (; h1 < h2; h1 >>= 1, h2 >>= 1) {
            if ((h1 & 1) != 0) {
                res = op.apply(res, _innerQuery(h1, w1, w2));
                h1++;
            }
            if ((h2 & 1) != 0) {
                h2--;
                res = op.apply(res, _innerQuery(h2, w1, w2));
            }
        }
        return res;
    }
    
}
