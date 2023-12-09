import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphQLButonPaneli extends JFrame {

    private List<CustomButton> buttons;
    private List<CustomButton> activeButtons = new ArrayList<>();

    private static final Color ACTIVE_COLOR = Color.GRAY;
    private static final Color INACTIVE_COLOR = Color.WHITE;
    private static final String GRAPHQL_API_ENDPOINT = "https://example.com/graphql";

    public GraphQLButonPaneli() {
        setTitle("GraphQL Buton Paneli");   // Pencere Başlığı "GraphQl Buton Paneli" dir.
        setSize(400, 400);        // Pencerenin genişliği ve yüksekliği 400 pikseldir.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      // Pncere kapatıldığında uygulamanın sonlanmasını sağlar.
        setLayout(new GridLayout(4, 4));  // 4 satır ve 4 sütündan oluştuğunu belirtir.

        buttons = new ArrayList<>();   //CustomButton türündeki butonları içeren bir liste oluşturur.

        for (int i = 0; i < 16; i++) {  // 16 defa dönen bir döngü oluşturur bu da 4*4 lük bir buton içerir.
            CustomButton button = createButton("Buton " + (i + 1), INACTIVE_COLOR);
            button.addActionListener(new ButtonClickListener());
            buttons.add(button);
            add(button);   // 16 tane buton oluşturur ve başlangıç değerleri pasif(beyaz) olarak ayarlanır.
        }

        resetButtonStates();   //Butonların durumunu sıfırlaması için bir fonk oluşturduk.
        setVisible(true);    //Pencerenin kullanıcıya görünür hale gelmesi içindir.
    }

    private void resetButtonStates() {
        for (CustomButton button : buttons) {
            button.setBackground(INACTIVE_COLOR);
        }
    }

    private CustomButton createButton(String buttonText, Color buttonColor) {
        return new CustomButton(buttonText, buttonColor);
    }

    private void handleButtonClick(CustomButton clickedButton) {
        if (activeButtons.contains(clickedButton)) {
            //Eğer activeButtons listesi clickedButton'ı içeriyorsa, bu buton daha önce aktive edilmiş demektir.
            deactivateButtons();  // aktif butonları pasif hale getirir.
        } else {
            //Bu buton daha önce aktive edilmemişse
            activateButton(clickedButton);  // butonun arka plan rengini aktif renk olarak ayarlar

            performMutation(clickedButton.getText());
             //clickedButton'ın metin içeriğini kullanarak bir mutasyon gerçekleştirme metodunu çağırır.
        }
    }

    private void activateButton(CustomButton button) {
        button.setBackground(ACTIVE_COLOR);
        activeButtons.add(button);
        /* CustomButton'ın arka plan rengini ACTIVE_COLOR (gri) olarak ayarlar.Bu butonun aktif olduğunu gösterir.*/
    }

    private void deactivateButtons() {
        for (CustomButton activeButton : activeButtons) {
            activeButton.setBackground(INACTIVE_COLOR);
          /*Her bir aktif butonun arka plan rengini INACTIVE_COLOR (beyaz) olarak ayarlar.Bu butonları pasif hale getirir.*/
        }
        activeButtons.clear();   // Aktif butonları temizler
    }

    private void performMutation(String buttonText) {  // Bu methodun amacı bir mutasyon gerçekleştirildiğini bildirmektir.
        System.out.println("Mutasyon gerçekleştirildi: " + buttonText);
        sendMutationToGraphQL(buttonText);      // GraphQL API'ye belirli bir bilgiyi iletmek için kullanılır.
    }

    private void sendMutationToGraphQL(String buttonText) {
      // Metodun amacı, bir mutation gerçekleştiğinde bu durumu bir konsol mesajıyla bildirmektir.

        System.out.println("Mutasyon, GraphQL API'ye gönderildi: " + buttonText);
    }
    private class CustomButton extends JButton {     // Özel JButton alt sınıfı
      //CustomButton, butonun metin içeriğini ve arkaplan rengini özelleştirmek için kullanılır.
        public CustomButton(String text, Color color) {
            super(text);
            setBackground(color);     //Butonun arkaplan rengini belirler.
        }
    }

    private class ButtonClickListener implements ActionListener {  // ActionListener için özel sınıf
       // ActionListener arayüzünü uygulayarak buton tıklamalarını dinleyen bir özel bir olay dinleyicisidir.
        @Override
        public void actionPerformed(ActionEvent e) {
            // Bir butona tıklama olayı gerçekleştiğinde, bu metod otomatik olarak çağrılır.

            CustomButton clickedButton = (CustomButton) e.getSource();
            handleButtonClick(clickedButton);
    }
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphQLButonPaneli::new);  //GraphQLButonPaneli nesnesini oluşturuduk.
    }
}