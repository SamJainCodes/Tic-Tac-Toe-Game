public class Data {
    
    private String config; // instance variables config is the board game in string rep left to right 
    private int score; 
    
    public Data(String config, int score){ // constructer which initialzies each instance variable with the given parameters 
        this.config = config;
        this.score = score;
    }

    public String getConfiguration(){ // getter method which returns config
        return this.config;
    }

    public int getScore(){ // getter method which returns score 
        return this.score;
    }
}
