package ProiectPOO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.Image;
import java.net.URL;

public class Interfata extends JFrame {


    private ArrayList<Aspirator> listaAspiratoare; // Lista ta principală
    private ArrayList<Masina_de_fum> listaMasini;
    private JTextField searchField;
    private JList<Aparat> resultsList; // Va afișa rezultatele
    private String tipselectat="Aspirator";
    private JLabel detailsLabel; // Pentru a afișa detaliile (toString-ul complet)
    private JPanel runPanel; // Panoul care conține câmpul de rulare și butonul
    private JTextField runTimeField;
    private String tipCurent="m²";
    private JLabel updateLabel;
    private Aspirator aspirator;
    private Masina_de_fum masinaFum;


    public Interfata(ArrayList<Aspirator> aspiratoare, ArrayList<Masina_de_fum> masini) {
        this.listaAspiratoare = aspiratoare;
        this.listaMasini = masini;

        setTitle("🎉 Super App Colorată 🎉");
        setSize(900, 600);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Color.CYAN);

        // 🔝 TOP
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.YELLOW);

        String[] tipuri = {"Aspirator", "Masina_de_fum"};
        JComboBox<String> combo = new JComboBox<>(tipuri);
        combo.setBackground(Color.ORANGE);

        searchField = new JTextField(15);
        searchField.setBackground(Color.WHITE);

        JButton searchBtn = new JButton("Căutare 🔍");
        searchBtn.setBackground(Color.GREEN);
        searchBtn.setForeground(Color.BLACK);

        topPanel.add(colorLabel("Tip:", Color.RED));
        topPanel.add(combo);
        topPanel.add(colorLabel("Nume:", Color.BLUE));
        topPanel.add(searchField);
        topPanel.add(searchBtn);

        add(topPanel, BorderLayout.NORTH);

        // 📋 LISTĂ
        resultsList = new JList<>();
        resultsList.setBackground(Color.WHITE);
        resultsList.setForeground(Color.BLUE);
        resultsList.setSelectionBackground(Color.RED);
        resultsList.setSelectionForeground(Color.YELLOW);

        add(new JScrollPane(resultsList), BorderLayout.CENTER);

        // 📄 DETAILS
        detailsLabel = new JLabel("Selectează ceva 😄");
        detailsLabel.setOpaque(true);
        detailsLabel.setBackground(Color.ORANGE);
        detailsLabel.setForeground(Color.BLUE);

        // ▶ RUN PANEL
        runPanel = new JPanel();
        runPanel.setBackground(Color.GREEN);

        runTimeField = new JTextField(5);
        runTimeField.setBackground(Color.orange);

        updateLabel = new JLabel("(m²)");
        updateLabel.setForeground(Color.RED);

        JButton runBtn = new JButton("▶ START 🚀");
        runBtn.setBackground(Color.BLUE);
        runBtn.setForeground(Color.WHITE);

        runPanel.add(colorLabel("Simulare:", Color.BLUE));
        runPanel.add(updateLabel);
        runPanel.add(runTimeField);
        runPanel.add(runBtn);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(Color.CYAN);
        bottom.add(detailsLabel, BorderLayout.NORTH);
        bottom.add(runPanel, BorderLayout.CENTER);

        add(bottom, BorderLayout.SOUTH);

        runPanel.setVisible(false);

        // 🎯 EVENTS
        searchBtn.addActionListener(e -> performSearch());

        combo.addActionListener(e -> {
            tipselectat = (String) combo.getSelectedItem();
            Updatare(tipselectat);
        });

        resultsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Aparat a = resultsList.getSelectedValue();

                if (a instanceof Aspirator) {
                    aspirator = (Aspirator) a;
                    detailsLabel.setText(formatText(aspirator.detalii()));
                    randomizeColors();
                    runPanel.setVisible(true);
                } else if (a instanceof Masina_de_fum) {
                    masinaFum = (Masina_de_fum) a;
                    detailsLabel.setText(formatText(masinaFum.detalii()));
                    randomizeColors();
                    runPanel.setVisible(true);
                }
            }
        });

        runBtn.addActionListener(e -> {
            int val = Integer.parseInt(runTimeField.getText());

            if (tipselectat.equals("Aspirator")) {
                aspirator.schimba_starea(aspirator.pornire(val));
                detailsLabel.setText(formatText(aspirator.detalii()));
            } else {
                masinaFum.schimba_starea(masinaFum.pornire(val));
                detailsLabel.setText(formatText(masinaFum.detalii()));
            }

            randomizeColors(); // 💥 culori noi la fiecare acțiune
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void randomizeColors() {
        Color[] colors = {
                Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                Color.PINK, Color.ORANGE, Color.CYAN, Color.MAGENTA
        };

        int i = (int)(Math.random() * colors.length);

        getContentPane().setBackground(colors[i]);
        runPanel.setBackground(colors[(i+1)%colors.length]);
        detailsLabel.setBackground(colors[(i+2)%colors.length]);
    }
    private JLabel colorLabel(String text, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(color);
        return lbl;
    }
    private String formatText(String text) {
        return "<html>" + text.replace("\n","<br>") + "</html>";
    }
    private void Updatare(String val){
        if(val.equals("Aspirator"))
        updateLabel.setText("<html>"+"(m²)"+"</html>");
        else
            updateLabel.setText("<html>"+"(m³)"+"</html>");
    }
    private void styleLabel(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JLabel) {
                c.setForeground(Color.WHITE);
            }
        }
    }
    private void styleField(JTextField field) {
        field.setBackground(new Color(70, 75, 85));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
    }
    private void styleButton(JButton btn, Color color) {
        btn.setFocusPainted(false);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
    }
    private void performSearch() {

        String searchText = searchField.getText().toLowerCase();
        List<Aparat> matchingAparate = new ArrayList<>();
if(tipselectat.equals("Aspirator")){
        for (Aspirator a : listaAspiratoare) {
            // Căutare simplă: numele aparatului conține textul de căutare
            if (a.toString().toLowerCase().contains(searchText)) {
                matchingAparate.add(a);
            }
        }}
else{
    for (Masina_de_fum m : listaMasini) {
        if (m.toString().toLowerCase().contains(searchText)) {
            matchingAparate.add(m);
        }
    }
}

        // Actualizează JList cu noile rezultate
        Aparat[] data = matchingAparate.toArray(new Aparat[0]);
        resultsList.setListData(data);
    }
}
