/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.fer.zemris.optjava.dz5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Adi
 */
public class Map {
    
    private double[][] distances;
    
    public Map(String filePath){
        
        
        try(BufferedReader br = new BufferedReader(new FileReader(filePath));){
            String s;

            while(!(s = br.readLine()).contains("DIMENSION"));
            String[] tmp = s.trim().split(" ");
            int dimension = Integer.parseInt(tmp[tmp.length - 1]);
            distances = new double[dimension][dimension];
            
            int inputType = 0;
            while(!(s = br.readLine()).contains("SECTION"));
            if(s.contains("NODE")){
                    inputType = 1;
            }
            
            switch(inputType){
                case 0:
                    for(int i = 0; i < dimension; ++i){
                        String[] values = br.readLine().replaceAll("\\s+", " ").trim().split(" ");
                        for(int j = 0; j < dimension; ++j){
                            distances[i][j] = Double.parseDouble(values[j]);
                        }
                    }
                    break;
                case 1:
                    ArrayList<Pair> coordinates = new ArrayList<>();
                    
                    for(int i = 0; i < dimension; ++i){
                        String[] values = br.readLine().replaceAll("\\s+", " ").trim().split(" ");
                        coordinates.add(new Pair(Double.parseDouble(values[1]), Double.parseDouble(values[2])));
                    }
                    
                    for(int i = 0; i < dimension; ++i){
                        for(int j = i + 1; j < dimension; ++j){
                            distances[i][j] = distances[j][i] = distance(coordinates.get(i), coordinates.get(j));
                        }
                    }
            }
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    public double getDistance(int i, int j){
        return distances[i][j];
    }
    
    public int cities(){
        return distances.length;
    }
    
    public List<Integer> neighbours(int i, int neighbours){
        
        Set<IndexPair> tmp  = new TreeSet<>();
        //Sortirati
        //Uzeti prvih n
        //Vratiti njihove indekse
        for(int j = 0; j < distances.length; ++j){
            tmp.add(new IndexPair(j, distances[i][j]));
        }
        
        List<Integer> res = new ArrayList();
        
        int j = 0;
        for(IndexPair ip : tmp){
            if(j != 0){
                res.add(ip.first);
                if(j == neighbours) break;
            }
            j++;
        }
        return res;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < distances.length; ++i){
            for(int j = 0; j < distances.length; ++j){
                sb.append(String.format("%.2f", distances[i][j])).append('\t');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
    
    private static double distance(Pair p1, Pair p2){
        double x = p1.first - p2.first;
        double y = p1.second - p2.second;
        return Math.sqrt(x*x + y*y);
    }
    
    private class Pair{
        public double first;
        public double second;
        
        public Pair(double first, double second){
            this.first = first;
            this.second = second;
        }
    }
    
    private class IndexPair implements Comparable{

        public int first;
        public double second;
        
        public IndexPair(int first, double second){
            this.first = first;
            this.second = second;
        }
        
        @Override
        public int compareTo(Object t) {
            IndexPair other = (IndexPair) t;
            if(this.second > other.second) return 1;
            if(this.second < other.second) return -1;
            return 0;
        }
        
    }
    
}
