package upc.edu.dsa.calculadora;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

// Importaciones de la nueva libreria para hacer los cálculos ...
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Ponemos nombres a los textos y botones de la App.
    TextView titulo, comandos, resultado;
    Button b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,bIgual,bSuma,bResta,bMult,bDiv,bTan,bAC,bSin,bCos;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Relacionamos los nombres que hemos puesto con los nombres que tienen en el layout.
        titulo = findViewById(R.id.Title);
        comandos = findViewById(R.id.Commands);
        resultado = findViewById(R.id.Results);

        assignId(b0,R.id.Button0);
        assignId(b1,R.id.Button1);
        assignId(b2,R.id.Button2);
        assignId(b3,R.id.Button3);
        assignId(b4,R.id.Button4);
        assignId(b5,R.id.Button5);
        assignId(b6,R.id.Button6);
        assignId(b7,R.id.Button7);
        assignId(b8,R.id.Button8);
        assignId(b9,R.id.Button9);
        assignId(bIgual,R.id.ButtonEqual);
        assignId(bSuma,R.id.ButtonAdd);
        assignId(bResta,R.id.ButtonSubstract);
        assignId(bMult,R.id.ButtonMultiply);
        assignId(bDiv,R.id.ButtonDivision);
        assignId(bTan,R.id.ButtonTangent);
        assignId(bAC,R.id.ButtonAC);
        assignId(bSin,R.id.ButtonSinus);
        assignId(bCos,R.id.ButtonCosinus);
    }

    void assignId(Button btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) { // Cuando se pulse cualquier botón ...
        Button boton = (Button) view;
        String botonText = boton.getText().toString();
        String dataToCalculate = comandos.getText().toString();

        if (botonText.equals("AC")){
            comandos.setText("");
            resultado.setText("0");
            return;
        }
        if (botonText.equals("=")){
            String finalResult = getResult(dataToCalculate);
            if(!finalResult.equals("Error")){
                resultado.setText(finalResult);
            } else {
                String aviso = "Err";
                resultado.setText(aviso);
            }
        } else {
            dataToCalculate = dataToCalculate + botonText;
            comandos.setText(dataToCalculate);
        }
    }

    String getResult(String data) {
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if (finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }
}