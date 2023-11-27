
    /*
     * 機能 : 任意の要素を変更した状態での全体のmexを取得が可能. 任意区間のmex取得はできない (尺取みたいにすればそれっぽいことはできる.)
     * 計算量 : 構築O(N) , 要素の更新O(logN) , mex取得O(logN);
     * 
     */

    class Mex {

        private record P(int val , int count) {
            @Override
            public String toString() {
                return "("+val+","+count+")";
            }
        }

        private SegmentTree<P> segtree ;
        private int Max ;

        Mex(int n) {
            this.Max = n + 1 ;
            init();
        }

        Mex(int [] array) {
            this.Max = array.length + 1 ;
            init();
            for(int i = 0 ; i < array.length ; i ++) if(0 <= array[i] && array[i] <= Max) {
                segtree.update(array[i] , new P(array[i] , segtree.query(array[i] , array[i] + 1).count + 1));
            }
        }

        private void init() {
            this.segtree = new SegmentTree<P>(Max + 1 , new P(inf , inf), (a , b) -> {
                if(a.count < b.count) return a ;
                else if(a.count > b.count) return b ;
                return a.val < b.val ? a : b ;
            });
            for(int i = 0 ; i <= Max ; i ++) segtree.update(i , new P(i , 0));
        }

        public void set(int next) {
            if(0 <= next && next <= Max) if(0 <= next && next <= Max) segtree.update(next , new P(next , segtree.query(next, next + 1).count + 1));
        }

        public void update(int prev , int next) {
            if(0 <= prev && prev <= Max) segtree.update(prev , new P(prev , segtree.query(prev , prev + 1).count - 1));
            if(0 <= next && next <= Max) segtree.update(next , new P(next , segtree.query(next, next + 1).count + 1));
        }

        public int mex() {
            return segtree.query(0 , Max + 1).val ;  
        }

    }
