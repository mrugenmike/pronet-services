package com.pronet.apriori;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;

public class AprioriService extends Observable {


    static ArrayList<String> results = new ArrayList<>();

    static String execTime = null;

    //@Scheduled(fixedDelay =1000,initialDelay=1000)
    public static ArrayList path(String career_id) throws Exception {

        AprioriService ap = new AprioriService(career_id);
        return results;
    }

    private List<int[]> itemsets ;
    private String transaFile;
    private int numItems;
    private int numTransactions;
    private double minSup;

    private boolean usedAsLibrary = false;

    public AprioriService(String career_id) throws Exception
    {
        configure(career_id);
        go();
    }

    private void go() throws Exception {
        long start = System.currentTimeMillis();

        createItemsetsOfSize1();
        int itemsetNumber=1;
        int nbFrequentSets=0;

        while (itemsets.size()>0)
        {

            calculateFrequentItemsets();

            if(itemsets.size()!=0)
            {
                nbFrequentSets+=itemsets.size();
                log("Found "+itemsets.size()+" frequent itemsets of size " + itemsetNumber + " (with support "+(minSup*100)+"%)");;
                createNewItemsetsFromPreviousOnes();
            }

            itemsetNumber++;
        }

        long end = System.currentTimeMillis();
        log("Execution time is: "+((double)(end-start)/1000) + " seconds.");
        log("Found "+nbFrequentSets+ " frequents sets for support "+(minSup*100)+"% (absolute "+Math.round(numTransactions*minSup)+")");
        log("Done");

        execTime= "Execution time is: "+((double)(end-start)/1000) + " seconds.";
        results.add(execTime);
    }
    private void foundFrequentItemSet(int[] itemset, int support) throws Exception{

        StringBuilder stringBuilder = new StringBuilder();
        Map<Integer,String> convert = new HashMap<>();

        convert.put(1,"Software Developer");convert.put(2,"Software Engineer");
        convert.put(3,"Team Lead");convert.put(4,"Principal Engineer");
        convert.put(5,"Software Architect");convert.put(6,"Data Analyst");
        convert.put(7,"Data Scientist");convert.put(8,"Data Engineer");
        convert.put(9,"Senior Software Developer");convert.put(10,"Senior Software Architect");
        convert.put(11,"Project Manager");convert.put(12,"Program Manager");
        convert.put(13,"CTO");convert.put(14,"CEO");

        if (usedAsLibrary) {
            this.setChanged();
            notifyObservers(itemset);
        }
        else {
            if(itemset.length >= 3)
                System.out.println(Arrays.toString(itemset) + "  ("+ ((support / (double) numTransactions))+" "+support+")");}
        if(itemset.length >= 3) {
            for (int i = 0; i < itemset.length; i++) {
                stringBuilder.append(convert.get(itemset[i]));
                if (i < itemset.length - 1)
                    stringBuilder.append(",");

            }

            results.add(stringBuilder.toString());

        }
    }

    private void log(String message) {
        if (!usedAsLibrary) {
            System.err.println(message);
        }
    }

    private void configure(String career_id) throws Exception
    {
        transaFile = "data/career.dat"; // default

        minSup = .2;// by default
        if (minSup>1 || minSup<0) throw new Exception("minSup: bad value");

        numItems = 0;
        numTransactions=0;
        BufferedReader data_in = new BufferedReader(new FileReader(transaFile));
        while (data_in.ready()) {
            String line=data_in.readLine();
            if (line.matches("\\s*")) continue; // be friendly with empty lines
            numTransactions++;
            StringTokenizer t = new StringTokenizer(line," ");
            while (t.hasMoreTokens()) {
                int x = Integer.parseInt(t.nextToken());
                //log(x);
                if (x+1>numItems) numItems=x+1;
            }
        }

        outputConfig();

    }

    private void outputConfig() {
        //output config info to the user
        log("Input configuration: "+numItems+" items, "+numTransactions+" transactions, ");
        log("minsup = "+minSup+"%");
    }

    private void createItemsetsOfSize1() {
        itemsets = new ArrayList<int[]>();
        for(int i=0; i<numItems; i++)
        {
            int[] cand = {i};
            itemsets.add(cand);
        }
    }

    private void createNewItemsetsFromPreviousOnes()
    {
        int currentSizeOfItemsets = itemsets.get(0).length;
        log("Creating itemsets of size "+(currentSizeOfItemsets+1)+" based on "+itemsets.size()+" itemsets of size "+currentSizeOfItemsets);

        HashMap<String, int[]> tempCandidates = new HashMap<String, int[]>(); //temporary candidates

        for(int i=0; i<itemsets.size(); i++)
        {
            for(int j=i+1; j<itemsets.size(); j++)
            {
                int[] X = itemsets.get(i);
                int[] Y = itemsets.get(j);

                assert (X.length==Y.length);

                int [] newCand = new int[currentSizeOfItemsets+1];
                for(int s=0; s<newCand.length-1; s++) {
                    newCand[s] = X[s];
                }

                int ndifferent = 0;
                for(int s1=0; s1<Y.length; s1++)
                {
                    boolean found = false;
                    // is Y[s1] in X?
                    for(int s2=0; s2<X.length; s2++) {
                        if (X[s2]==Y[s1]) {
                            found = true;
                            break;
                        }
                    }
                    if (!found){ // Y[s1] is not in X
                        ndifferent++;
                        newCand[newCand.length -1] = Y[s1];
                    }

                }

                assert(ndifferent>0);


                if (ndifferent==1) {
                    Arrays.sort(newCand);
                    tempCandidates.put(Arrays.toString(newCand),newCand);
                }
            }
        }

        //set the new itemsets
        itemsets = new ArrayList<int[]>(tempCandidates.values());
        log("Created "+itemsets.size()+" unique itemsets of size "+(currentSizeOfItemsets+1));

    }

    private void line2booleanArray(String line, boolean[] trans) {
        Arrays.fill(trans, false);
        StringTokenizer stFile = new StringTokenizer(line, " ");
        while (stFile.hasMoreTokens())
        {

            int parsedVal = Integer.parseInt(stFile.nextToken());
            trans[parsedVal]=true;
        }
    }


    private void calculateFrequentItemsets() throws Exception
    {

        log("Passing through the data to compute the frequency of " + itemsets.size()+ " itemsets of size "+itemsets.get(0).length);

        List<int[]> frequentCandidates = new ArrayList<int[]>();

        boolean match;
        int count[] = new int[itemsets.size()];
        BufferedReader data_in = new BufferedReader(new InputStreamReader(new FileInputStream(transaFile)));

        boolean[] trans = new boolean[numItems];

        for (int i = 0; i < numTransactions; i++) {

            String line = data_in.readLine();
            line2booleanArray(line, trans);

            for (int c = 0; c < itemsets.size(); c++) {
                match = true;
                int[] cand = itemsets.get(c);
                for (int xx : cand) {
                    if (trans[xx] == false) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    count[c]++;
                }
            }

        }

        data_in.close();

        for (int i = 0; i < itemsets.size(); i++) {

            if ((count[i] / (double) (numTransactions)) >= minSup) {
                foundFrequentItemSet(itemsets.get(i),count[i]);
                frequentCandidates.add(itemsets.get(i));
            }
        }

        itemsets = frequentCandidates;
    }
}
