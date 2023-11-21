    class SCANLINE
    {
        List<Integer> [] segment ;
        List<IntPair> [] border ;
        long [] query ;
        FenwickTree bit ;
        int l , r ;
        /*
         * l,rの区間に収まりきらない場合、座圧する必要あり.
         */
        PlaneSweep(int N , int Q)
        {
            this.segment = new ArrayList[N];
            this.border = new ArrayList[N];
            this.bit = new FenwickTree(N);
            this.query = new long[Q];
            this.l = N ;
            this.r = 0 ;
            for(int i = 0 ; i < N ; i ++ )
            {
                segment[i] = new ArrayList<>();
                border[i] = new ArrayList<>();
            }
        }
        // 目標の区間
        public void add(int l , int r)
        {
            segment[l].add(r);
        }
        // 目標を包含するべき区間
        public void add(int l , int r , int index)
        {
            border[l].add(new IntPair(r, index));
        }
        // 結果を返す
        public long query(int index)
        {
            return query[index];
        }
        
        void start()
        {
            for(int x = l - 1 ; x >= 0 ; x -- )
            {   
                for(int y : segment[x]) bit.add(y, 1);
                // 問題によって異なる.
                for(var p : border[x]) query[p.se] = bit.sum(0 , p.fi + 1);
            }
        }
    }