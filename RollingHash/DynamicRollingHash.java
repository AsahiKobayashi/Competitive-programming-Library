class DynamicRollingHash {

    private final int n ;
    private final int size ;
    private final BinaryOperator<HashData> op ;
    private final HashData e ;
    private final HashData [] data;
    final long base = 31 ;
    final long Hash_MOD = 1000000009;
    private long [] power ;
    private HashData [] array ;

    @SuppressWarnings("unchecked")

    public DynamicRollingHash(String s) {
        this.n = s.length() ;
        power = new long[n + 1];
        array = new HashData[n];
        power[0] = 1 ;
        for(int i = 1 ; i < power.length ; i ++) {
            power[i] = power[i - 1] * base % Hash_MOD ; 
        }
        for(int i = 0 ; i < n ; i ++) {
            HashData o = new HashData();
            o.hash = s.charAt(i) ;
            o.length = 1 ;
            array[i] = o ;
        }
        int k = 1;
        while (k < n) k <<= 1;
        this.size = k;
        this.e = new HashData() ;
        this.op = (a , b) -> {
            HashData o = new HashData();
            o.hash = (a.hash + b.hash * power[a.length]) % Hash_MOD ;
            o.length = a.length + b.length ;
            return o ;
        };
        this.data = new HashData[size << 1];
        Arrays.fill(data, e);
        build(array);
    }

    private void build(HashData [] dat) {
        int l = dat.length;
        System.arraycopy(dat, 0, data, size, l);
        for (int i = size - 1; i > 0; i--) {
            data[i] = op.apply(data[i << 1 | 0], data[i << 1 | 1]);
        }
    }

    private void update(int p , HashData x) {
        assert 0 <= p && p < n : "p=" + p;
        data[p += size] = x;
        p >>= 1;
        while (p > 0) {
            data[p] = op.apply(data[p << 1 | 0], data[p << 1 | 1]);
            p >>= 1;
        }
    }

    public void update(int p , char c) {
        HashData o = query(p);
        o.hash = c ;
        update(p , o);
    } 

    public long hash(int l , int r) {
        return query(l, r).hash ;
    }

    private HashData query(int p) {
        assert 0 <= p && p < n : "p=" + p;

        return data[p + size];
    }

    private HashData query(int l, int r) {
        assert 0 <= l && l <= r && r <= n : "l=" + l + ", r=" + r;

        HashData sumLeft = e;
        HashData sumRight = e;
        l += size;
        r += size;
        while (l < r) {
            if ((l & 1) == 1) sumLeft = op.apply(sumLeft, data[l++]);
            if ((r & 1) == 1) sumRight = op.apply(data[--r], sumRight);
            l >>= 1;
            r >>= 1;
        }
        return op.apply(sumLeft, sumRight) ;
    }

    private class HashData {
        long hash ;
        int length ;
    }

}
