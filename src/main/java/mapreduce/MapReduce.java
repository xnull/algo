package mapreduce;

import java.lang.*;
import java.util.*;

class Main {
  //https://dzone.com/articles/word-count-hello-word-program-in-mapreduce
  public static void main(String[] args) {
     
     //You are given sentences, find words which appears more than two times
     List<String> result = new MapReduce().execute();
     System.out.println(result);
  }
}

class MapReduce {

  List<String> execute(){
    String sentence1 = "hey hello";
    String sentence2 = "you hey";
    String sentence3 = "hey you hello";
    
    Mapper mapper1 = new Mapper();
    Mapper mapper2 = new Mapper();
    Mapper mapper3 = new Mapper();

    Reducer reducer1 = new Reducer();
    Reducer reducer2 = new Reducer();
    Reducer reducer3 = new Reducer();

    Map<String, Integer> res1 = mapper1.map(sentence1);
    Map<String, Integer> res2 = mapper2.map(sentence2);
    Map<String, Integer> res3 =mapper3.map(sentence3);
    
    List<Reducer> reducers = new ArrayList<>();
    reducers.add(reducer1);
    reducers.add(reducer2);
    reducers.add(reducer3);

    List<Set<KV>> intermediateSplitting = groupByKey(res1, res2, res3);
    
    List<String> finalResult = new ArrayList<>();
    for(int i = 0; i < intermediateSplitting.size(); i++){
      finalResult.add(
        reducers.get(i).reduce(intermediateSplitting.get(i))
      );
    }

    return finalResult;
  }

  private List<Set<KV>> groupByKey(Map<String, Integer> map1, Map<String, Integer> map2, Map<String, Integer> map3){
      List<Set<KV>> result = new ArrayList<>();
      result.add(new HashSet<>());
      result.add(new HashSet<>());
      result.add(new HashSet<>());

      redistribute(map1, result);
      redistribute(map2, result);
      redistribute(map3, result);

      return result;
  }

  private void redistribute(Map<String, Integer> mapa, List<Set<KV>> result){
    for(String key: mapa.keySet()){
          if(key.equals("hey")){
            result.get(0).add(new KV(key, mapa.get(key)));
          }
          if(key.equals("hello")){
            result.get(1).add(new KV(key, mapa.get(key)));
          }
          if(key.equals("you")){
            result.get(2).add(new KV(key, mapa.get(key)));
          }
      }
  }
}

class Mapper {

  Map<String, Integer> map(String sentence) {
    Map<String, Integer> mapa = new HashMap<>();

    for(String word: sentence.split(" ")){
      mapa.put(word, mapa.getOrDefault(word, 0) + 1);
    }

    return mapa;
  }
}

class Reducer {
  
  //Returns list of words met more than two times 
  String reduce(Set<KV> splitted){
    
    int treshold = 0;
    String result = null;
    for(KV kv: splitted) {
      treshold += kv.value;
      if(treshold > 2) {
        result = kv.key;
        break;
      }
    }

    return result;
  }
}

class KV {
  String key;
  Integer value;

  KV(String key, Integer value){
    this.key = key;
    this.value = value;
  }
}
