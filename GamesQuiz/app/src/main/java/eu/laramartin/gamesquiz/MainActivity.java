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

        displayQuoteAndAnswers();

        optionOneTextView.setOnClickListener(choiceOneOnClickListener);
        optionTwoTextView.setOnClickListener(choiceTwoOnClickListener);
        optionThreeTextView.setOnClickListener(choiceThreeOnClickListener);
        nextButton.setOnClickListener(nextButtonOnClickListener);
    }


    final View.OnClickListener choiceOneOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (! isTurnFinished){
                isOptionChosenCorrect(0);
                isTurnFinished = true;
            }
        }
    };

    final View.OnClickListener choiceTwoOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (! isTurnFinished){
                isOptionChosenCorrect(1);
                isTurnFinished = true;
            }

        }
    };

    final View.OnClickListener choiceThreeOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (! isTurnFinished){
                isOptionChosenCorrect(2);
                isTurnFinished = true;
            }
        }
    };

    final View.OnClickListener nextButtonOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (isTurnFinished) {
                beginNextTurn();
            }
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
//        Collections.shuffle(items);
    }

    private Quote getOneQuoteFromList(int index){
        return listOfQuotes.get(index);
    }

    private void getOptions(){
        threeDiffOptions.add(quotesDisplayed);
        threeDiffOptions.add(20 + quotesDisplayed);
        threeDiffOptions.add(40 + quotesDisplayed);
    }

    private void getThreeQuotesFromListToDisplayAsAnswer(){
        if (quotesDisplayed == 10){
            //reset();
            Toast.makeText(MainActivity.this, "more than 10 turns", Toast.LENGTH_SHORT).show();
        }
        getOptions();
        shuffleList(threeDiffOptions);
        optionOneQuote = getOneQuoteFromList((Integer) threeDiffOptions.get(0));
        optionTwoQuote = getOneQuoteFromList((Integer) threeDiffOptions.get(1));
        optionThreeQuote = getOneQuoteFromList((Integer) threeDiffOptions.get(2));
        addThreeOptionsToList(optionOneQuote, optionTwoQuote, optionThreeQuote);
    }

    private void addThreeOptionsToList(Quote one, Quote two, Quote three){
        orderOptions.add(one);
        orderOptions.add(two);
        orderOptions.add(three);
    }

    private void displayQuoteAndAnswers(){
        actualQuote = getOneQuoteFromList(quotesDisplayed);
        quoteTextView.setText(actualQuote.phrase);

        getThreeQuotesFromListToDisplayAsAnswer();
        optionOneTextView.setText(optionOneQuote.game);
        optionTwoTextView.setText(optionTwoQuote.game);
        optionThreeTextView.setText(optionThreeQuote.game);
        quotesDisplayed += 1;
        counterTextView.setText(quotesDisplayed + "/10");
    }

    private String getSelectedQuote(int option){
        String selectedGame = null;
        if (option == 0){
            selectedGame = optionOneQuote.game;
        } else if (option == 1) {
            selectedGame = optionTwoQuote.game;
        } else if (option == 2) {
            selectedGame = optionThreeQuote.game;
        }
        return selectedGame;
    }

    private TextView getSelectedTextView(int option){
        TextView selectedTextView = null;
        if (option == 0){
            selectedTextView = optionOneTextView;
        } else if (option == 1){
            selectedTextView = optionTwoTextView;
        } else if (option == 2){
            selectedTextView = optionThreeTextView;
        }
        return selectedTextView;
    }

    private void isOptionChosenCorrect(int option){
        TextView selectedTextView = getSelectedTextView(option);
        if (actualQuote.game != getSelectedQuote(option)){
            markOptionChosenAsWrong(selectedTextView);
            return;
        }
        markOptionChosenAsCorrect(selectedTextView);
    }

    private void markOptionChosenAsCorrect(TextView chosen){
        chosen.setBackgroundColor(Color.parseColor("#49C684"));
        makeNextButtonVisible();
        checkIfGameHasFinished();
    }

    private void markOptionChosenAsWrong(TextView chosen){
        chosen.setBackgroundColor(Color.parseColor("#F55E7A"));
        int indexOfCorrectAnswer = orderOptions.indexOf(actualQuote);
        TextView correctAnswerTextView = getSelectedTextView(indexOfCorrectAnswer);
        correctAnswerTextView.setBackgroundColor(Color.parseColor("#49C684"));
        makeNextButtonVisible();
        checkIfGameHasFinished();
    }

    private void checkIfGameHasFinished(){
        if (isGameFinished){
            Toast.makeText(MainActivity.this, "game ended", Toast.LENGTH_SHORT).show();
        } else if (isTurnFinished) {
            beginNextTurn();
        }
    }

    private void beginNextTurn(){
        isTurnFinished = false;
        threeDiffOptions.clear();
        orderOptions.clear();
        resetAnswersBackgroundColor();
        makeNextButtonInvisible();
        getThreeQuotesFromListToDisplayAsAnswer();
        displayQuoteAndAnswers();
    }

    private void makeNextButtonVisible(){
        nextButton.setVisibility(View.VISIBLE);
    }

    private void makeNextButtonInvisible(){
        nextButton.setVisibility(View.INVISIBLE);
    }

    private void resetAnswersBackgroundColor(){
        optionOneTextView.setBackgroundColor(0x00000000);
        optionTwoTextView.setBackgroundColor(0x00000000);
        optionThreeTextView.setBackgroundColor(0x00000000);
    }

}
