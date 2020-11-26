package org.uvigo.esei.dm.financialskill.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.uvigo.esei.dm.financialskill.R;
import org.uvigo.esei.dm.financialskill.core.Expense;
import org.uvigo.esei.dm.financialskill.core.FSApplication;
import org.uvigo.esei.dm.financialskill.db.ExpenseFacade;
import org.w3c.dom.Text;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ExpenseFacade EXPENSEFACADE;
    private ExpenseCursorAdapter EXPENSECURSORADAPTER;
    private TextView TEXTVIEW_BALANCE; //Is this correct...?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EXPENSEFACADE = new ExpenseFacade(((FSApplication)getApplication()).getDbManager());
        Cursor cursor = EXPENSEFACADE.getExpenses();
        EXPENSECURSORADAPTER = new ExpenseCursorAdapter(MainActivity.this, cursor, EXPENSEFACADE);

        ListView LISTVIEWEXPENSES = findViewById(R.id.listViewExpenses);
        LISTVIEWEXPENSES.setAdapter(EXPENSECURSORADAPTER);

        TEXTVIEW_BALANCE = findViewById(R.id.textViewBalanceTotal); //Declared globally... is this correct?
        Button BUTTON_EXPENSE = findViewById(R.id.buttonExpense);
        Button BUTTON_INCOME = findViewById(R.id.buttonIncome);

        BUTTON_INCOME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddIncomeDialog();

                //This updates on BUTTON click, not after executing showAddExpenseDialog()
                Double balance = EXPENSEFACADE.getTotalBalance();
                TEXTVIEW_BALANCE.setText(String.format(Locale.US, "%.2f", balance));
            }
        });

        BUTTON_EXPENSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddExpenseDialog();

                //This updates on BUTTON click, not after executing showAddExpenseDialog()
                Double balance = EXPENSEFACADE.getTotalBalance();
                TEXTVIEW_BALANCE.setText(String.format(Locale.US, "%.2f", balance));
            }
        });

        //SWAP CURSOR TO ALWAYS UPDATE BALANCE
        EXPENSECURSORADAPTER.swapCursor(EXPENSEFACADE.getExpenses());

        
        Double balance = EXPENSEFACADE.getTotalBalance();

        TEXTVIEW_BALANCE.setText(String.format(Locale.US, "%.2f", balance));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.optionDeleteAll){
            showDeleteAllDialog();
            return true;
        }
        return false;
    }

    private void showDeleteAllDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete all transactions");
        builder.setMessage("All transactions will be deleted. Are you sure?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EXPENSEFACADE.removeAllExpenses();
                EXPENSECURSORADAPTER.swapCursor(EXPENSEFACADE.getExpenses());
                Double balance = EXPENSEFACADE.getTotalBalance();
                TEXTVIEW_BALANCE.setText(String.format(Locale.US, "%.2f", balance)); //Declared globally. Is there another way to update?
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }
    private void showAddIncomeDialog() {

        LinearLayout LAYOUT = new LinearLayout(MainActivity.this);
        LAYOUT.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Add a new income");

        final EditText EDITTEXT_CONCEPT = new EditText(MainActivity.this);
        EDITTEXT_CONCEPT.setText("Concept...");

        final EditText EDITTEXT_QUANTITY = new EditText(MainActivity.this);
        EDITTEXT_QUANTITY.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        EDITTEXT_QUANTITY.setRawInputType(Configuration.KEYBOARD_12KEY);
        EDITTEXT_QUANTITY.setText("0.00");

        final EditText EDITTEXT_DESCRIPTION = new EditText(MainActivity.this);
        EDITTEXT_DESCRIPTION.setText("Description...");

        LAYOUT.addView(EDITTEXT_CONCEPT);
        LAYOUT.addView(EDITTEXT_QUANTITY);
        LAYOUT.addView(EDITTEXT_DESCRIPTION);

        builder.setView(LAYOUT);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String concept = EDITTEXT_CONCEPT.getText().toString();
                Double quantity = Double.parseDouble(EDITTEXT_QUANTITY.getText().toString());
                String description = EDITTEXT_DESCRIPTION.getText().toString();
                Expense expense = new Expense(concept, quantity, "INCOME", description);
                EXPENSEFACADE.addExpense(expense);
                EXPENSECURSORADAPTER.swapCursor(EXPENSEFACADE.getExpenses());
                Double balance = EXPENSEFACADE.getTotalBalance();
                TEXTVIEW_BALANCE.setText(String.format(Locale.US, "%.2f", balance)); //Declared globally. Is there another way to update?
            }
        });
        builder.create().show();
    }

    private void showAddExpenseDialog() {

        LinearLayout LAYOUT = new LinearLayout(MainActivity.this);
        LAYOUT.setOrientation(LinearLayout.VERTICAL);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Add a new expense");

        final EditText EDITTEXT_CATEGORY= new EditText(MainActivity.this);
        EDITTEXT_CATEGORY.setText("Category...");

        final EditText EDITTEXT_CONCEPT = new EditText(MainActivity.this);
        EDITTEXT_CONCEPT.setText("Concept...");

        final EditText EDITTEXT_QUANTITY = new EditText(MainActivity.this);
        EDITTEXT_QUANTITY.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        EDITTEXT_QUANTITY.setRawInputType(Configuration.KEYBOARD_12KEY);
        EDITTEXT_QUANTITY.setText("0.00");

        final EditText EDITTEXT_DESCRIPTION = new EditText(MainActivity.this);
        EDITTEXT_DESCRIPTION.setText("Description...");

        LAYOUT.addView(EDITTEXT_CATEGORY);
        LAYOUT.addView(EDITTEXT_CONCEPT);
        LAYOUT.addView(EDITTEXT_QUANTITY);
        LAYOUT.addView(EDITTEXT_DESCRIPTION);

        builder.setView(LAYOUT);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String concept = EDITTEXT_CONCEPT.getText().toString();
                Double quantity = Double.parseDouble(EDITTEXT_QUANTITY.getText().toString());
                String category = EDITTEXT_CATEGORY.getText().toString();
                String description = EDITTEXT_DESCRIPTION.getText().toString();
                Expense expense = new Expense(concept, quantity*-1, category, description);
                EXPENSEFACADE.addExpense(expense);
                EXPENSECURSORADAPTER.swapCursor(EXPENSEFACADE.getExpenses());
                Double balance = EXPENSEFACADE.getTotalBalance();
                TEXTVIEW_BALANCE.setText(String.format(Locale.US, "%.2f", balance)); //Declared globally. Is there another way to update?
            }
        });
        builder.create().show();
    }
}