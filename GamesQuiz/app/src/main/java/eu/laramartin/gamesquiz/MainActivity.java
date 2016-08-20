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

    int quotesAlreadyDisplayed = 8;
    int correctAnswers = 0;

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
        nextButton.setText(R.string.next);

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
            if (!isTurnFinished && !isGameFinished){
                isOptionChosenCorrect(0);
                isTurnFinished = true;
            }
        }
    };

    final View.OnClickListener choiceTwoOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (! isTurnFinished && !isGameFinished){
                isOptionChosenCorrect(1);
                isTurnFinished = true;
            }
        }
    };

    final View.OnClickListener choiceThreeOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (!isTurnFinished && !isGameFinished){
                isOptionChosenCorrect(2);
                isTurnFinished = true;
            }
        }
    };

    final View.OnClickListener nextButtonOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (isTurnFinished) {
                beginNextTurn();
            } else if (isGameFinished) {
                resetGame();
            }
        }
    };

    private void shuffleList(List items){
        // seed 0 for testing
//        Collections.shuffle(items, new Random(0));
        Collections.shuffle(items);
    }

    private Quote getOneQuoteFromList(int index){
        return listOfQuotes.get(index);
    }

    private void getOptions(){
        threeDiffOptions.add(quotesAlreadyDisplayed);
        threeDiffOptions.add(20 + quotesAlreadyDisplayed);
        threeDiffOptions.add(40 + quotesAlreadyDisplayed);
    }

    private void getThreeQuotesFromListToDisplayAsAnswer(){
        if (quotesAlreadyDisplayed == 10){
            isGameFinished = true;
            finalGameDisplay();
            return;
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
        if (isGameFinished){
            return;
        }
        actualQuote = getOneQuoteFromList(quotesAlreadyDisplayed);
        quoteTextView.setText(actualQuote.phrase);

        getThreeQuotesFromListToDisplayAsAnswer();
        optionOneTextView.setText(optionOneQuote.game);
        optionTwoTextView.setText(optionTwoQuote.game);
        optionThreeTextView.setText(optionThreeQuote.game);
        quotesAlreadyDisplayed += 1;
        counterTextView.setText(quotesAlreadyDisplayed + "/10");
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
        correctAnswers += 1;
    }

    private void markOptionChosenAsCorrect(TextView chosen){
        chosen.setBackgroundColor(Color.parseColor("#49C684"));
        makeNextButtonVisible();
        checkIfTurnHasFinished();
    }

    private void markOptionChosenAsWrong(TextView chosen){
        chosen.setBackgroundColor(Color.parseColor("#F55E7A"));
        int indexOfCorrectAnswer = orderOptions.indexOf(actualQuote);
        TextView correctAnswerTextView = getSelectedTextView(indexOfCorrectAnswer);
        correctAnswerTextView.setBackgroundColor(Color.parseColor("#49C684"));
        makeNextButtonVisible();
        checkIfTurnHasFinished();
    }

    private void checkIfTurnHasFinished(){
//        if (isGameFinished){
//            Toast.makeText(MainActivity.this, "game ended", Toast.LENGTH_SHORT).show();
//            finalGameDisplay();
//            //return;
//        } else if (isTurnFinished) {
//            beginNextTurn();
//        }
        if (isTurnFinished) {
            beginNextTurn();
        }
    }

    private void finalGameDisplay(){
        notDisplayAnswers();
        finalGameResults();
        nextButton.setText(R.string.reset);
        makeNextButtonVisible();
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

    private void notDisplayAnswers(){
        optionOneTextView.setText("");
        optionTwoTextView.setText("");
        optionThreeTextView.setText("");
    }
    private void finalGameResults(){
        quoteTextView.setText("Correct answers:\n\n" + correctAnswers);
    }

    private void resetGame(){
        isTurnFinished = false;
        isGameFinished = false;
        threeDiffOptions.clear();
        orderOptions.clear();
        correctAnswers = 0;
        quotesAlreadyDisplayed = 0;
        shuffleList(listOfQuotes);
        getThreeQuotesFromListToDisplayAsAnswer();
        displayQuoteAndAnswers();
        nextButton.setText(R.string.next);
        makeNextButtonInvisible();
    }

}
