# Iterator
### Overview
All type of Iterators and their usage

##### 1. FilterIterator
    Iterator<Integer> it = Arrays.asList(1,2,3,4).iterator();
    Predicate<Integer> evenPredicate = new Predicate<Integer>(){
        public boolean test(Integer i){
            return i%2==0;
        }
     };
     Iterator<Integer> filterIterator = new FilterIterator<Integer>(evenPredicate, it);
     //print 2 and 4
     while(filterIterator.hasNext()){
        System.out.println(filterIterator.next());
     }
##### 2. MergeIterator
    Iterator<Integer> it1 = Arrays.asList(1,4,10).iterator();
    Iterator<Integer> it2 = Arrays.asList(2,7,8).iterator();
    Iterator<Integer> it3 = Arrays.asList(5,6).iterator();

    MergeIterator<Integer> mergeIterator = new MergeIterator<>(it1, null, it2, it3);
    //print 1,2,4,5,6,7,8,10
    while (mergeIterator.hasNext()){
        System.out.println(mergeIterator.next());
    }
##### 3. PowersetIterator
    Iterator<Integer> it = Arrays.asList(1,2,3,4).iterator();
    PowersetIterator<Integer> powerSet = new PowersetIterator<Integer>(it,3,4);
    while(powerSet.hasNext()){
         //print [1,2,3],[2,3,4],[1,3,4],[1,2,4],[1,2,3,4]
        System.out.println(powerSet.next());
    }
##### 4. TransformationIterator
    Function<Integer,Integer> function = integer -> integer+2;
    Iterator<Integer> it = Arrays.asList(1,2,3).iterator();
    Iterator<Integer> transformationIterator =
                    new TransformationIterator<>(it,function);
    while (transformationIterator.hasNext()){
         //print 3,4,5
         System.out.println(transformationIterator.next());
    }
    
