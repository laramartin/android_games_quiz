package eu.laramartin.gamesquiz;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<Quote> listOfQuotes = new ArrayList<>();
    ArrayList threeDiffOptions = new ArrayList();
    ArrayList orderOptions = new ArrayList();

    TextView counterTextView;
    TextView quoteTextView;
    TextView optionOneTextView;
    TextView optionTwoTextView;
    TextView optionThreeTextView;
    Button nextButton;
    Quote optionOneQuote;
    Quote optionTwoQuote;
    Quote optionThreeQuote;
    Quote actualQuote;
    boolean isTurnFinished = false;
    boolean isGameFinished = false;

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
        nextButton = (Button) findViewById(R.id.buttonNext);

        GameQuotes.initQuotes(listOfQuotes);
        shuffleList(listOfQuotes);

        displayQuoteAndOptions();

        optionOneTextView.setOnClickListener(choiceOneOnClickListener);
        optionTwoTextView.setOnClickListener(choiceTwoOnClickListener);
        optionThreeTextView.setOnClickListener(choiceThreeOnClickListener);

    }


    final View.OnClickListener choiceOneOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            isOptionChosenCorrect(0);
            isTurnFinished = true;
        }
    };

    final View.OnClickListener choiceTwoOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            isOptionChosenCorrect(1);
            isTurnFinished = true;
        }
    };

    final View.OnClickListener choiceThreeOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            isOptionChosenCorrect(2);
            isTurnFinished = true;
        }
    };

//    private int randomNumber(){
//        Random r = new Random();
//        int length = listOfQuotes.size();
//        int num = r.nextInt(length - 1) + 1;
//        return num;
//    }

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
        addThreeOptionsToList(optionOneQuote, optionTwoQuote, optionThreeQuote);
    }

    private void addThreeOptionsToList(Quote one, Quote two, Quote three){
        orderOptions.add(one);
        orderOptions.add(two);
        orderOptions.add(three);
    }

    private void displayQuoteAndOptions(){
        actualQuote = getQuoteFromList(quotesDisplayed);
        quoteTextView.setText(actualQuote.phrase);

        getQuotesFromList();
        optionOneTextView.setText(optionOneQuote.game);
        optionTwoTextView.setText(optionTwoQuote.game);
        optionThreeTextView.setText(optionThreeQuote.game);
        quotesDisplayed += 1;
        counterTextView.setText(quotesDisplayed + "/10");
    }

    private void isOptionChosenCorrect(int option){
        if (option == 0) {
            if (actualQuote.game != optionOneQuote.game) {
                markOptionChosenAsWrong(optionOneTextView);
                return;
            }
            markOptionChosenAsCorrect(optionOneTextView);
        } else if (option == 1){
            if (actualQuote.game != optionTwoQuote.game){
                markOptionChosenAsWrong(optionTwoTextView);
                return;
            }
            markOptionChosenAsCorrect(optionTwoTextView);
        } else if (option == 2){
            if (actualQuote.game != optionThreeQuote.game){
                markOptionChosenAsWrong(optionThreeTextView);
                return;
            }
            markOptionChosenAsCorrect(optionThreeTextView);
        }
    }

    private void markOptionChosenAsCorrect(TextView chosen){
        chosen.setBackgroundColor(Color.parseColor("#49C684"));
        makeNextButtonVisible();
        checkIfGameHasFinished();
    }

    private void markOptionChosenAsWrong(TextView chosen){
        chosen.setBackgroundColor(Color.parseColor("#F55E7A"));
        makeNextButtonVisible();
        checkIfGameHasFinished();
    }

    private void checkIfGameHasFinished(){
        if (! isGameFinished){
            Toast.makeText(MainActivity.this, "game ended", Toast.LENGTH_SHORT).show();
        } else {
            beginNextTurn();
        }
    }

    private void beginNextTurn(){
        makeNextButtonInvisible();
    }

    private void makeNextButtonVisible(){
        nextButton.setVisibility(View.VISIBLE);
    }

    private void makeNextButtonInvisible(){
        nextButton.setVisibility(View.INVISIBLE);
    }

}
