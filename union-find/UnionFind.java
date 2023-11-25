
class UnionFind {

        private int [] rank , parents , size ;
        private int n , group_count ;
        private List<Integer> [] group ;

        @SuppressWarnings("unchecked")
        UnionFind(int n) {
            this.n = n ;
            this.rank = new int[n];
            this.parents = new int[n];
            this.size = new int[n];
            this.group_count = n ; 
            this.group = new ArrayList[n];
            for(int i = 0 ; i < n ; i ++) {
                parents[i] = i ;
                rank[i] = 0 ;
                size[i] = 1 ;
                group[i] = new ArrayList<>();
            }
        }

        public int group() { 
            return this.group_count ;
        }

        public int size(int x) { 
            return size[root(x)] ;
        }

        public int root(int x) {
            if(x == parents[x]) return x ;
            else parents[x] = root(parents[x]);
            return parents[x];
        }

        public boolean same(int x , int y) { 
            return root(x) == root(y) ; 
        }

        public void unite(int l , int r) {
            int left  = root(l);
            int right = root(r);
            int parent = -1 , child = -1 ;
            if(left == right) return ;
            parent = rank[left] > rank[right] ? left : right;
            child = rank[left] > rank[right] ? right : left ;
            --group_count ;
            parents[child] = parent ;
            if(rank[parent] == rank[child]) rank[parent] ++ ;
            size[parent] += size[child] ; 
        }

        public List<Integer> [] group_List() {
            for(int i = 0 ; i < n ; i ++) group[root(i)].add(i);
            return group ;
        }

}
