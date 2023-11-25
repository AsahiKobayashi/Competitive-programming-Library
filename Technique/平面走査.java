    /*
     * 機能 : 任意の区間内に含まれる、条件を満たす範囲の個数を求める. 
     * 計算量 : 1クエリにつきO(logN)
     */

    class Scanline {
        
        private List<Integer> [] segment ;
        private List<Pair<Integer,Integer>> [] border ;
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
            border[l].add(new Pair<Integer,Integer>(r, index));
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