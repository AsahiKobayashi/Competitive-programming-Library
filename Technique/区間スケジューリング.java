    class Scheduling {
        
        private List<Pair<Long,Long>> plan ;
        private int count ;
        private long current_time ;
        private int size ;

        Scheduling(int n) {
            this.plan = new java.util.ArrayList<>();
            this.count = 0 ;
            this.current_time = 0 ;
            this.size = n ;
        }

        void add(long l , long r) {
            plan.add(new Pair<Long,Long>(l , r));
        }

        int result() {
            Collections.sort(plan , Comparator.comparing(Pair<Long,Long>::se));
            for(int i = 0 ; i < size ; i ++) {
                if(current_time <= plan.get(i).fi) {
                    current_time = plan.get(i).se;
                    count ++ ;
                }
            }
            return count ;
        }

    }