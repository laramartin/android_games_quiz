package eu.laramartin.gamesquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<Quote> listOfQuotes = new ArrayList<>();
    TextView counterTextView;
    TextView quoteTextView;
    TextView optionOneTextView;
    TextView optionTwoTextView;
    TextView optionThreeTextView;
    int quotesDisplayed = 0;

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
        shuffleQuotesList();

        displayQuoteAndOptions();

    }

    private int randomNumber(){
        Random r = new Random();
        int length = listOfQuotes.size();
        int num = r.nextInt(length - 1) + 1;
        return num;
    }

    private void shuffleQuotesList(){
        // shuffle existing list and pick 10
        // seed 0 for testing
        Collections.shuffle(listOfQuotes, new Random(0));
        //Collections.shuffle(listOfQuotes);
    }

    private void displayQuoteAndOptions(){
        Quote actualQuote = listOfQuotes.get(quotesDisplayed);
        quoteTextView.setText(actualQuote.phrase);
        optionOneTextView.setText(actualQuote.game);
        optionTwoTextView.setText(actualQuote.game);
        optionThreeTextView.setText(actualQuote.game);
        quotesDisplayed += 1;
        counterTextView.setText(quotesDisplayed + "/10");
    }


}
