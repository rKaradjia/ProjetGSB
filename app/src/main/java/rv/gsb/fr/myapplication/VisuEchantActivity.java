package rv.gsb.fr.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class VisuEchantActivity extends AppCompatActivity {

    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_echant);

        Bundle paquet = this.getIntent().getExtras();
        String paquetRecu = paquet.getString("lesechantillons");

        info = (TextView) findViewById(R.id.echantillons);
        info.setText(paquetRecu);
    }
}
