class WeightedUnionFind {

    private int[] parent , diff_weight;

    WeightedUnionFind(int nmemb) {
        parent = new int[nmemb];
        diff_weight = new int[nmemb];
        Arrays.fill(parent, -1);
        Arrays.fill(diff_weight, 0);
    }

    private int root(int x) {
        if (parent[x] < 0) return x;
        int r = root(parent[x]);
        diff_weight[x] += diff_weight[parent[x]];
        parent[x] = r;

        return parent[x];
    }

    private int weight(int x) {
        root(x);
        return diff_weight[x];
    }

    public int diff(int x, int y) {
        return weight(y) - weight(x);
    }
    
    public boolean isSameGroup(int x, int y) {
        return root(x) == root(y);
    }

    public void unite(int x, int y, int w) {
        if (isSameGroup(x, y)) {
            return;
        }
        w += weight(x);
        w -= weight(y);
        x = root(x);
        y = root(y);
        parent[y] = x;
        diff_weight[y] = w;
    }

    public int groubSize(int x) {
        return -parent[root(x)];
    }

}
