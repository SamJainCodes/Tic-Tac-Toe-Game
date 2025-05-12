import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

public class HashDictionary implements DictionaryADT { // implements the ADT

    private int size; // size of the table
    private ArrayList<LinkedList<Data>> table; // actual hash table implemented using ArrayList

    public HashDictionary(int size){ // contructer for an object of this class
        
        this.size = size; // constructer sets size to the given size and creates a hash table with it 
        table = new ArrayList<>(size);

        for(int i = 0; i < size; i++){ // for each "bucket" or index in the hash table we create an empty link list for seperate chaining
            table.add(new LinkedList<Data>());
        }
    }
    
    public int put (Data record) throws DictionaryException{ // this method adds a record 
        
        String config = record.getConfiguration(); // get the configuration to check for duplicates 
        int index = polyH(config, size); // using this private function to find the hashcode
        LinkedList<Data> linkedList = table.get(index); // using the get function to get the linked list at the hash code or the index found previously 

        Iterator<Data> iterator = linkedList.iterator(); // create iterator to iterate through linked list

        while(iterator.hasNext()){
            Data data = iterator.next();
            if (data.getConfiguration().equals(config)){
                throw new DictionaryException();  // Throw exception since its already exists and we dont want duplicates.
            }
        }

       
        if(linkedList.isEmpty()){ // Since Linked List is empty, there is no room for a collison hence we add and return 0.
            linkedList.add(record);
            return 0;
        }
        else{ // Since there is a collision, we add and return 1.
            linkedList.add(record);
            return 1;
        }
        
    }

    public void remove (String config) throws DictionaryException{ // this method removes a record with the given config
       
        boolean foundConfig = false; // true if we removed the record
        int index = polyH(config, size); // using this private function to find the hashcode
        LinkedList<Data> linkedList = table.get(index); // using the get function to get the linked list at the hash code or the index found previously 

        Iterator<Data> iterator = linkedList.iterator(); // create iterator to iterate through linked list

        while(iterator.hasNext()){
            Data data = iterator.next();
            if(data.getConfiguration().equals(config)){ // if record found, remove and turn foundConfig true 
                iterator.remove();
                foundConfig = true;
                break;
            }
        }

        if(foundConfig == false){ // if we didnt find the record, we throw an exception since there is nothing to remove
            throw new DictionaryException();
        }


    }

    public int get (String config){ // this method gets a record with the given config

        int index = polyH(config, size); // using this private function to find the hashcode
        LinkedList<Data> linkedList = table.get(index); // using the get function to get the linked list at the hash code or the index found previously 

        Iterator<Data> iterator = linkedList.iterator(); // create iterator to iterate through linked list

        while(iterator.hasNext()){ // iterate through each record and if found the record with the given config, return the score
            Data data = iterator.next();
            if(data.getConfiguration().equals(config)){
                return data.getScore();
            }
        }

        return -1; // if record not found, return -1 

    }

    public int numRecords(){ // returns total num of records 
        
        int totalRecords = 0; // counter for num of records

        for(int i = 0; i < size; i++){ // loop through each bucket 
            LinkedList<Data> linkedList = table.get(i); // get the link list at each bucket 
            
            Iterator<Data> iterator = linkedList.iterator();

            while(iterator.hasNext()){ // iterate through each link list and incerement counter 
                iterator.next();
                totalRecords++;
            }
        }

        return totalRecords; // return the total number after for loop is complete

    }

    private int polyH(String config, int size){ // private method which returns the hash code 
        
        int val = (int)(config.charAt(0)); // returns the first char 
        int x = 13; // this is trhe prime number we use to multiply

        for(int i = 1; i < config.length(); i++){ // looping through each char
            val = (val * x + (int)(config.charAt(i))) % size; // we convert char to int add them together and then use the modulo to stay within our size 
        }

        return val; // return the index or hash code calcuated after all operations are complete
    }
}
