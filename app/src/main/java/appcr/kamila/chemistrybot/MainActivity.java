package appcr.kamila.chemistrybot;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText message;
    ImageView send;
    List<MessageModel>list;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerView);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);

        list = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageAdapter(list);
        recyclerView.setAdapter(adapter);

        list.add(new MessageModel("Добрый день!\n" +
                "Для начала работы выберите номер химической реакции из списка.\n" +
                "1. Fe + S = FeS\n" +
                "2. 2Al + 3H2O = Al2O3 + 3H2\n" +
                "3. 2HgO = 2Hg + O2\n" +
                "4. 4NH3 + 5O2 = 4NO + 6H2O\n" +
                "5. Fe + CuSO4 = FeSO4 + Cu\n" +
                "6. Zn + 2HCl = ZnCl2 + H2\n" +
                "7. MgO + H2SO4 = MgSO4 + Н2О\n" +
                "8. C + O2 = CO2\n" +
                "9. Na2O + CO2 = Na2CO3\n" +
                "10. NH3 + CO2 + H2O = NH4HCO3\n" +
                "11. 2Ag2O = 4Ag + O2\u00AD\n" +
                "12. (NH4)2Cr2O7 = N2\u00AD + Cr2O3 + 4H2O\u00AD\n" +
                "13. 2NaI + Cl2 = 2NaCl + I2\n" +
                "14. CaCO3 + SiO2 = CaSiO3 + CO2\u00AD\n" +
                "15. Ba(OH)2 + H2SO4 = BaSO4 + 2H2O\n" +
                "16. 6Li + N2 = 2Li3N\n" +
                "17. AgNO3 + NaCl = AgCl + NaNO3\n" +
                "18. Общая характеристика и химические свойства щелочных металлов\n" +
                "19. Гидриды,оксиды,пероксиды,гидроксиды щелочных металлов получение\n" +
                "20. Взаимодействие с растворами щелочей а)амфотерных металлов; б)неметаллов; в)кислотных оксидов; г)амфотерных оксидов", MessageModel.SENT_BY_BOT));
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(adapter.getItemCount());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = message.getText().toString();

                if (question.isEmpty()){
                    Toast.makeText(MainActivity.this, "Вам нужно что-то написать", Toast.LENGTH_SHORT).show();
                }else{
                    addToChat(question, MessageModel.SENT_BY_ME);
                    message.setText("");
                }
            }
        });
    }

    private void addToChat(String question, String sentByMe) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                list.add(new MessageModel(question, sentByMe));
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());

                String botResponse = BotResponses.responses.get(question.toLowerCase());
                if (botResponse != null) {
                    list.add(new MessageModel(botResponse, MessageModel.SENT_BY_BOT));
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
                } else {
                    list.add(new MessageModel("Извините, но я не могу на это ответить.", MessageModel.SENT_BY_BOT));
                    adapter.notifyDataSetChanged();
                    recyclerView.smoothScrollToPosition(adapter.getItemCount());
                }
            }
        });
    }
}