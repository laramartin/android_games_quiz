package eu.laramartin.gamesquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<Quote> listOfQuotes = new ArrayList<>();
    ArrayList threeDiffOptions = new ArrayList();
    TextView counterTextView;
    TextView quoteTextView;
    TextView optionOneTextView;
    TextView optionTwoTextView;
    TextView optionThreeTextView;
    Quote optionOneQuote;
    Quote optionTwoQuote;
    Quote optionThreeQuote;

    int quotesDisplayed = 0;
    int correctAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterTextView = (TextView) findViewById(R.id.counter);
        quoteTextView = (TextView) findViewById(R.id.quote);
        optionOneTextView = (TextView) findViewById(R.id.optionOne);
        optionTwoTextView = (TextView) findViewById(R.id.optionTwo);
        optionThreeTextView = (TextView) findViewById(R.id.optionThree);

        GameQuotes.initQuotes(listOfQuotes);
        shuffleList(listOfQuotes);

        displayQuoteAndOptions();

    }

    private int randomNumber(){
        Random r = new Random();
        int length = listOfQuotes.size();
        int num = r.nextInt(length - 1) + 1;
        return num;
    }

    private void shuffleList(List items){
        // shuffle existing list and pick 10
        // seed 0 for testing
        Collections.shuffle(items, new Random(0));
        //Collections.shuffle(items);
    }

    private Quote getQuoteFromList(int index){
        return listOfQuotes.get(index);
    }

    private void getOptions(){
        threeDiffOptions.add(quotesDisplayed);
        threeDiffOptions.add(20);
        threeDiffOptions.add(40);
    }

    private void getQuotesFromList(){
        getOptions();
        shuffleList(threeDiffOptions);
        optionOneQuote = getQuoteFromList((Integer) threeDiffOptions.get(0));
        optionTwoQuote = getQuoteFromList((Integer) threeDiffOptions.get(1));
        optionThreeQuote = getQuoteFromList((Integer) threeDiffOptions.get(2));
    }

    private void displayQuoteAndOptions(){
        Quote actualQuote = getQuoteFromList(quotesDisplayed);
        quoteTextView.setText(actualQuote.phrase);

        getQuotesFromList();
        optionOneTextView.setText(optionOneQuote.game);
        optionTwoTextView.setText(optionTwoQuote.game);
        optionThreeTextView.setText(optionThreeQuote.game);
        quotesDisplayed += 1;
        counterTextView.setText(quotesDisplayed + "/10");
    }


}
