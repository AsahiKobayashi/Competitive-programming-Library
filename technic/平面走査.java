// 任意の区間内に含まれる、条件を満たす範囲の個数を求める. O(logN)

class Scanline {
        
        private List<Integer> [] segment ;
        private List<IntPair> [] border ;
        private long [] query ;
        private FenwickTree bit ;
        private int l , r ;

        Scanline(int N , int Q) {
            this.segment = new ArrayList[N];
            this.border = new ArrayList[N];
            this.bit = new FenwickTree(N);
            this.query = new long[Q];
            this.l = N ;
            this.r = 0 ;
            for(int i = 0 ; i < N ; i ++ ) {
                segment[i] = new ArrayList<>();
                border[i] = new ArrayList<>();
            }
        }
        // 条件を満たす区間
        public void add(int l , int r) {
            segment[l].add(r);
        }
        // クエリ区間
        public void add(int l , int r , int index) {
            border[l].add(new IntPair(r, index));
        }
        // 結果を返す
        public long query(int index) {
            return query[index];
        }
        
        public void start() {
            for(int x = l - 1 ; x >= 0 ; x -- ) {   
                for(int y : segment[x]) bit.add(y, 1);
                for(var p : border[x]) query[p.se] = bit.sum(0 , p.fi + 1);
            }
        }
        
}

class FenwickTree {

    private int _n;
    private long[] data;

    public FenwickTree(int n){
        this._n = n;
        data = new long[n];
    }

    public FenwickTree(long[] data) {
        this(data.length);
        build(data);
    }

    public void set(int p, long x){
        add(p, x - get(p));
    }

    public void add(int p, long x){
        assert(0<=p && p<_n);
        p++;
        while(p<=_n){
            data[p-1] += x;
            p += p&-p;
        }
    }

    public long sum(int l, int r){
        assert(0<=l && l<=r && r<=_n);
        return sum(r)-sum(l);
    }

    public long get(int p){
        return sum(p, p+1);
    }

    private long sum(int r){
        long s = 0;
        while(r>0){
            s += data[r-1];
            r -= r&-r;
        }
        return s;
    }

    private void build(long[] dat) {
        System.arraycopy(dat, 0, data, 0, _n);
        for (int i=1; i<=_n; i++) {
            int p = i+(i&-i);
            if(p<=_n){
                data[p-1] += data[i-1];
            }
        }
    }
}

