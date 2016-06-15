package com.example.tanat.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.EnumMap;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3, button4, button5, button6, button7, button8, button9,
            buttonResult, buttonDel, buttonAdd, buttonPercent, buttonDot, buttonZero, buttonSubtract,
            buttonMultipli,buttonDivide;
    EditText textResult;
    TextView textView;
    String symbol;

    private OperationType operType;

    private EnumMap<Symbol, Object> commands = new EnumMap<Symbol, Object>(Symbol.class); // хранит все введенные данные пользователя

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = (EditText) findViewById(R.id.txtResult);
        textView = (TextView) findViewById(R.id.textView);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        buttonResult = (Button) findViewById(R.id.buttonResult);
        buttonDel = (Button) findViewById(R.id.buttonDel);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonPercent = (Button) findViewById(R.id.buttonPercent);
        buttonDot = (Button) findViewById(R.id.buttonDot);
        buttonZero = (Button) findViewById(R.id.buttonZero);
        buttonSubtract = (Button) findViewById(R.id.buttonSubtract);
        buttonMultipli = (Button) findViewById(R.id.buttonMultipli);
        buttonDivide = (Button) findViewById(R.id.buttonDivide);

        buttonAdd.setTag(OperationType.ADD);
        buttonDivide.setTag(OperationType.DIVIDE);
        buttonMultipli.setTag(OperationType.MULTIPLY);
        buttonSubtract.setTag(OperationType.SUBTRACT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void buttonClick(View view) {

        switch (view.getId()) {
            case R.id.buttonAdd:
            case R.id.buttonSubtract:
            case R.id.buttonDivide:
            case R.id.buttonMultipli: {// кнопка - одна из операций
    //            commands.remove(Symbol.OPERATION);
                commands.remove(Symbol.OPERATION);

                operType = (OperationType) view.getTag();// получить тип операции из кнопки

                switch (operType) {
                    case ADD:
                        symbol = " + ";
                        break;
                    case SUBTRACT:
                        symbol = " - ";
                        break;
                    case DIVIDE:
                        symbol = " / ";
                        break;
                    case MULTIPLY:
                        symbol = " * ";
                        break;
                }

                if (!commands.containsKey(Symbol.OPERATION)) {                          //если раньше операции не было
                    commands.put(Symbol.OPERATION, operType);
    //                commands.put(Symbol.OPERATION, operType);
                    if (!commands.containsKey(Symbol.FIRST_DIGIT)) {                    //если первое число пустое
                        //                if ((commands.get(Symbol.FIRST_DIGIT)).equals("")){
                        commands.put(Symbol.FIRST_DIGIT, textResult.getText());
        //                commands.put(Symbol.OPERATION, operType);
                        textView.setText(commands.get(Symbol.FIRST_DIGIT).toString() + symbol);

                        textResult.setText("0");
                    } else {
                        if (!commands.containsKey(Symbol.SECOND_DIGIT)) {
                            if (!textResult.equals("0"))
                                textView.setText(commands.get(Symbol.FIRST_DIGIT).toString()
                                        + symbol);

                            commands.put(Symbol.SECOND_DIGIT, textResult.getText());        //добавляем второе число в масс
                            doCalc();                                                       //считаем
                            //        commands.put(Symbol.OPERATION, operType);                       //запоминаем операцию
        //                    commands.remove(Symbol.SECOND_DIGIT);                           //удаляем второй символ
                        }
                    }
                } else {
                    commands.put(Symbol.FIRST_DIGIT, textResult.getText());
                    textResult.setText("0");
                }

            //    textView.setText(textResult.getText());
                break;
            }

            case R.id.buttonClear: { // кнопка очистить
                textView.setText("");
                textResult.setText("0");
                commands.clear();// стереть все введенные команды
                break;
            }

            case R.id.buttonResult: {// кнопка посчитать результат
                if (commands.containsKey(Symbol.FIRST_DIGIT) && commands.containsKey(Symbol.OPERATION)) {// если ввели число, нажали знак операции и ввели второе число
                    commands.put(Symbol.SECOND_DIGIT, textResult.getText());
                    textView.setText("");
    //                textView.setText(commands.get(Symbol.FIRST_DIGIT).toString() + symbol +
    //                        commands.get(Symbol.SECOND_DIGIT).toString() + " =");

                    doCalc(); // посчитать

                    commands.put(Symbol.OPERATION, operType);// записать тип операции для последующего подсчета
                    commands.remove(Symbol.SECOND_DIGIT);
                }

                break;
            }

            case R.id.buttonDot: { // кнопка для ввода десятичного числа

                if (commands.containsKey(Symbol.FIRST_DIGIT)
                        && getDouble(textResult.getText().toString()) == getDouble(commands
                        .get(Symbol.FIRST_DIGIT).toString())

                        ) {

                    textResult
                            .setText("0" + view.getContentDescription().toString());
                }

                if (!textResult.getText().toString().contains(",")) {
                    textResult.setText(textResult.getText() + ",");
                }
                break;
            }

            case R.id.buttonDel: { // кнопка удаления последнего символа
            /*    txtResult.setText(txtResult.getText().delete(
                        txtResult.getText().length() - 1,
                        txtResult.getText().length()));
            */
                textResult.setText(textResult.getText().subSequence(0, textResult.length() - 1));

                if (textResult.getText().toString().trim().length() == 0) {
                    textResult.setText("0");
                }

                break;
            }

            default: { // все остальные кнопки (цифры
                if (textResult.getText().toString().equals("0"))
                    textResult.setText(view.getContentDescription().toString());
                else {
                    textResult.setText(textResult.getText()
                            + view.getContentDescription().toString());
                    }
            }
        }
    }

    private double getDouble(Object value) {
        double result = 0;
        try {
            result = Double.valueOf(value.toString().replace(',', '.')).doubleValue();// замена запятой на точку
        } catch (Exception e) {
            e.printStackTrace();
            result = 0;
        }

        return result;
    }

    private void doCalc() {

        OperationType operTypeTmp = (OperationType) commands.get(Symbol.OPERATION);

        double result = calc(operTypeTmp,
                getDouble(commands.get(Symbol.FIRST_DIGIT)),
                getDouble(commands.get(Symbol.SECOND_DIGIT)));

        if (result % 1 == 0){
            textResult.setText(String.valueOf((int)result));// отсекать нули после запятой
        }else{
            textResult.setText(String.valueOf(result));// отсекать нули после запятой
        }

        textView.setText(commands.get(Symbol.FIRST_DIGIT).toString()
                + symbol + commands.get(Symbol.SECOND_DIGIT).toString() + " =");

        commands.remove(Symbol.FIRST_DIGIT);
        commands.remove(Symbol.SECOND_DIGIT);
        commands.remove(Symbol.OPERATION);
    //    commands.put(Symbol.FIRST_DIGIT, result);// записать полученный результат в первое число, чтобы можно было сразу выполнять новые операции
    }

    private Double calc(OperationType operType, double a, double b) {
        switch (operType) {
            case ADD: {
                return CalcOperations.add(a, b);
            }
            case DIVIDE: {
                return CalcOperations.divide(a, b);
            }
            case MULTIPLY: {
                return CalcOperations.multiply(a, b);
            }
            case SUBTRACT: {
                return CalcOperations.subtract(a, b);
            }
        }

        return null;
    }
}
