class UnionFind <K> {

        private Map<K, K> parent;
        private Map<K, Integer> rank;
        private int group_count ;

        UnionFind(int N) {
            this.group_count = N ;
            parent = new HashMap<>();
            rank = new HashMap<>();
        }

        public int group() {
            return this.group_count ;
        }

        public void set(K key) {
            if(parent.containsKey(key)) return ;
            parent.put(key, key);
            rank.put(key, 0);
        }

        public K root(K key) {
            if (!parent.containsKey(key)) throw new IllegalArgumentException("Key not found!");

            if (!parent.get(key).equals(key)) {
                parent.put(key, root(parent.get(key)));
            }
            return parent.get(key);
        }

        public void unite(K key1, K key2) {
            K left = root(key1);
            K right = root(key2);
            K par = null , ch = null ;
            if(left.equals(right)) return ;
            --group_count;
            par = rank.get(left) > rank.get(right) ? left : right ;
            ch = rank.get(left) > rank.get(right) ? right : left ;
            parent.put(ch , par);
            rank.put(par , rank.getOrDefault(par , 0) + 1);
        }

        public boolean same(K key1, K key2) {
            return root(key1).equals(root(key2));
        }

}
