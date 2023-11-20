    class PartialPersistentUnionFind {

        final int inf = 1 << 30 ;
        int n ;
        int [] par , rank , time ;
        int [] group_size ;
        PartialPersistentArray vertex ;
        PartialPersistentArray edge ;

        @SuppressWarnings("unchecked")
        public PartialPersistentUnionFind(int n) {
            this.n = n;
            par = new int[n];
            rank = new int[n];
            time = new int[n];
            group_size = new int[n];
            fill(group_size , 1);
            for (int i = 0; i < n ; i ++) par[i] = i ;
            vertex = new PartialPersistentArray(group_size);
            edge = new PartialPersistentArray(new int[n]);
            Arrays.fill(time, inf);
        }

        public int root(int x , int t) {
            return time[x] > t ? x : root(par[x] , t);
        }

        public int root(int x) {
            return x == par[x] ? x : root(par[x]);
        }

        public int size(int x , int t) {
            x = root(x , t);
            return vertex.get(x , t);
        }

        public int edges(int x , int t) {
            x = root(x , t);
            return edge.get(x , t);
        } 

        public void unite(int x , int y , int t) {
            x = root(x , t);
            y = root(y , t);
            if (x == y) {
                edge.set(y , edges(y , t) + 1 , t);
                return;
            }
            if (rank[x] < rank[y]) {
                int tmp = x;
                x = y;
                y = tmp;
            }
            vertex.set(x , size(x, t) + size(y, t) , t);
            edge.set(x , edges(x, t) + edges(y , t) + 1 , t);
            par[y] = x ;
            time[y] = t ;
            if (rank[x] == rank[y]) ++ rank[x];
        }

        public boolean issame(int x, int y, int t) {
            return root(x , t) == root(y , t);
        }

    }