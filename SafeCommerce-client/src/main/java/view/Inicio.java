package view;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscosGroup;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.processos.ProcessosGroup;
import com.github.britooo.looca.api.util.Conversor;
import com.opencsv.CSVWriter;
import dao.Conexao;
import dao.Leitura;
import dao.Parametro;
import dao.ParametroDao;
import dao.Servidor;
import dao.ServidorDAO;
import dao.Usuario;
import util.GetMac;
import io.nayuki.qrcodegen.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.math.BigInteger;
import java.net.NetworkInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.swing.JButton;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;
import oshi.hardware.NetworkIF;

/**
 *
 * @author aluno
 */
public class Inicio extends javax.swing.JFrame {
    /**
     * Creates new form Inicio
     */
    static DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    static JFreeChart lineChart;
    static ChartPanel chartPanel;
    private GetMac gm;
    private String mac;
    private ServidorDAO serverDao = new ServidorDAO();
    private Conexao connection = new Conexao();
    private JdbcTemplate con = connection.getConnection();
    private ParametroDao parametroDao = new ParametroDao();
    private Looca looca = new Looca();
    private Processador proc;
    private Servidor servidor;
    private Conversor conversor;
    private List<Parametro> parametros;
    private Integer fkServidor;

    public Inicio(Usuario user, String enderecoMac) {
        try {
            inicializarValores(enderecoMac);
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        getContentPane().setBackground(new Color(255, 255, 255));
        initComponents(user);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setTitle("SafeCommerce - Monitoramento");
        ImageIcon img = new ImageIcon(getClass().getResource("/img/logo.png"));
        setIconImage(img.getImage());
        dataset.addValue(2, "a", "d");

    }

    public void criarCSV(Integer fkServidor, Integer fkMetrica, String valor, String componente) throws IOException {
        String[] header = {"fkServidor", "fkMetrica", "dataLeitura", "valor_leitura", "componente"};
        //System.out.println(componente);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.nnn");
        LocalDateTime now = LocalDateTime.now();
        String[] record1 = {String.valueOf(fkServidor), String.valueOf(fkMetrica), dtf.format(now), valor, componente};

        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(record1);
        String caminhoTemp = System.getProperty("java.io.tmpdir") + "/insert.csv";
        caminhoTemp = caminhoTemp.replace("\\", "/");
        //System.out.println(caminhoTemp);
        try (CSVWriter writer = new CSVWriter(new FileWriter(System.getProperty("java.io.tmpdir") + "/insert.csv"))) {
            writer.writeAll(list);
        }
        String esquel = " LOAD DATA LOCAL INFILE '" + caminhoTemp
                + "' INTO TABLE Leitura "
                + " FIELDS TERMINATED BY \',\' ENCLOSED BY \'\"'"
                + " LINES TERMINATED BY \'\\n\'";
        StopWatch timer = new StopWatch();
        timer.start();
        con.update(esquel);
        timer.stop();
        //System.out.println(timer.getTotalTimeSeconds());

    }

    public void inicializarValores(String enderecoMac) throws Exception {
        servidor = serverDao.getServidorByMac(enderecoMac);
        proc = looca.getProcessador();
        conversor = new Conversor();
        /*mac = gm.getMac();
        mac = mac.replace("-", ":");
        Servidor servidor = serverDao.getServidorByMac(mac);*/
        fkServidor = servidor.getIdServidor();
        parametros = parametroDao.getParametros(fkServidor);
    }
    
    
    
    private void Monitorando(Double cpu, Double ram, Double disco) throws Exception {
        Long lDisco = looca.getGrupoDeDiscos().getDiscos().get(0).getBytesDeLeitura();
        Long eDisco = looca.getGrupoDeDiscos().getDiscos().get(0).getBytesDeEscritas();
        ProcessosGroup pc = looca.getGrupoDeProcessos();
        List<Processo> processos = pc.getProcessos().stream().distinct().collect(Collectors.toList());

        Collections.sort(processos, new Comparator<Processo>() {
            @Override
            public int compare(Processo n1, Processo n2) {
                Double uso1 = n1.getUsoCpu();
                Double uso2 = n2.getUsoCpu();
                return uso1.compareTo(uso2);
            }
        });

        for (Parametro parametro : parametros) {
            Integer atual = parametro.getFkMetrica();
            System.out.println(atual + " metrica tal");
            String cpuFormat = String.format("%.1f", cpu);

            if (atual == 1) {
                criarCSV(fkServidor, atual, String.valueOf(proc.getUso()), "CPU");
                // con.update("INSERT INTO Leitura VALUES (?, ?, NOW(), ?, 'CPU')", fkServidor, atual, (proc.getUso()));
            } else if (atual == 2) {
                criarCSV(fkServidor, atual, String.valueOf(proc.getNumeroCpusLogicas()), "CPU");
                // con.update("INSERT INTO Leitura VALUES (?, ?, NOW(), ?, 'CPU')", fkServidor, atual, (proc.getNumeroCpusLogicas()));
            } else if (atual == 3) {
                criarCSV(fkServidor, atual, String.valueOf(proc.getFrequencia()).replace(",", "."), "CPU");
                //con.update("INSERT INTO Leitura VALUES (?, ?, NOW(), ?, 'CPU')", fkServidor, atual, (cpuFormat));
            } else if (atual == 4) {
                criarCSV(fkServidor, atual, String.valueOf(proc.getFrequencia()).replace(",", "."), "CPU");
                //con.update("INSERT INTO Leitura VALUES (?, ?, NOW(), ?, 'CPU')", fkServidor, atual, (proc.getFrequencia()));
            } else if (atual == 5) {
                criarCSV(fkServidor, atual, String.valueOf(conversor.formatarBytes(looca.getMemoria().getTotal())).replace(" GiB", "").replace(",", "."), "RAM");
                //con.update("INSERT INTO Leitura VALUES (?, ?, NOW(), ?, 'CPU')", fkServidor, atual, (looca.getMemoria().getTotal()));
            } else if (atual == 6) {
                criarCSV(fkServidor, atual, String.valueOf(ram), "RAM");
                //con.update("INSERT INTO Leitura VALUES (?, ?, NOW(), ?, 'Ram')", fkServidor, atual, ram);
            } else if (atual == 7) {
                criarCSV(fkServidor, atual, conversor.formatarBytes(looca.getGrupoDeDiscos().getTamanhoTotal()).replace(" GiB", "").replace(",", "."), "Disco");
                //con.update("INSERT INTO Leitura VALUES (?, ?, NOW(), ?, 'Disco')", fkServidor, atual, conversor.formatarBytes(looca.getGrupoDeDiscos().getTamanhoTotal()));
            } else if (atual == 8) {
                criarCSV(fkServidor, atual, String.valueOf(disco), "Disco");
                //con.update("INSERT INTO Leitura VALUES (?, ?, NOW(), ?, 'Disco')", fkServidor, atual, disco);
            } else if (atual == 9) {
                criarCSV(fkServidor, atual, String.valueOf(conversor.formatarSegundosDecorridos(lDisco)), "Disco");
                //con.update("INSERT INTO Leitura VALUES (?, ?, NOW(), ?, 'Disco')", fkServidor, atual, looca.getGrupoDeDiscos());
            } else if (atual == 10) {
                criarCSV(fkServidor, atual, String.valueOf(conversor.formatarSegundosDecorridos(eDisco)), "Disco");
                //con.update("INSERT INTO Leitura VALUES (?, ?, NOW(), ?, 'Disco')", fkServidor, atual, looca.getGrupoDeDiscos());
            } else if (atual == 11) {
                //CRIA O INSERT DA TEMPERATURA AQ DUARTE
            } else if (atual == 12) {
                List<String> nomesProcessos = new ArrayList();
                for (int i = processos.size() - 1; i >= processos.size() - 10; i--) {
                    if (!processos.get(i).getNome().equals("Idle") && !nomesProcessos.contains(processos.get(i).getNome())) {
                        criarCSV(fkServidor, atual, String.valueOf(processos.get(i).getPid()), processos.get(i).getNome());
                    }
                }
            } else if (atual == 13) {
                List<String> nomesProcessos = new ArrayList();
                for (int i = processos.size() - 1; i >= processos.size() - 10; i--) {
                    if (!processos.get(i).getNome().equals("Idle") && !nomesProcessos.contains(processos.get(i).getNome())) {
                        criarCSV(fkServidor, atual, String.valueOf(processos.get(i).getUsoCpu() / 10), processos.get(i).getNome());
                    }
                }
            } else if (atual == 14) {
                List<String> nomesProcessos = new ArrayList();
                for (int i = processos.size() - 1; i >= processos.size() - 10; i--) {
                    if (!processos.get(i).getNome().equals("Idle") && !nomesProcessos.contains(processos.get(i).getNome())) {
                        criarCSV(fkServidor, atual, String.valueOf(processos.get(i).getUsoMemoria()), processos.get(i).getNome());
                    }
                }
            }
        }

    }

    // List<Parametro> parametrosG;
    /*List<Leitura> leituraCpu = leitura.getCpu();
    List<Leitura> leituraRam = leitura.getRam();
    List<Leitura> leituraDisco = leitura.getDisco();*/
    private static final int N = 100;
    private static final Random random = new Random();

    private XYSeriesCollection gerarDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeries cpuSeries = new XYSeries("CPU");
        final XYSeries ramSeries = new XYSeries("RAM");
        final XYSeries discoSeries = new XYSeries("Disco");
        Looca looca = new Looca();
        Memoria ram = looca.getMemoria();

        Processador cpu = looca.getProcessador();

        DiscosGroup rom = looca.getGrupoDeDiscos();
        Conversor conversor = new Conversor();

        List<Volume> volumes = rom.getVolumes();
        Double usoVolume = 0d;
        Long totalVolume = 0l;

        for (Volume volume : volumes) {
            totalVolume += volume.getTotal();
            Long totalVolumeAtual = volume.getTotal();
            Long volumeDisponivel = volume.getDisponivel();
            usoVolume += Double.valueOf(totalVolumeAtual) - Double.valueOf(volumeDisponivel);
        }
        Double porcentagemVolume = (usoVolume / totalVolume) * 100;

        Long ramUso = ram.getEmUso();
        Long ramTotal = ram.getTotal();
        Double porcentagemRam = (Double.valueOf(ramUso) / Double.valueOf(ramTotal)) * 100;

        for (int i = 0; i < 2; i++) {
            cpuSeries.add(i, cpu.getUso());
            ramSeries.add(i, porcentagemRam);
            discoSeries.add(i, porcentagemVolume);

        }
        new Timer(1, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // colocar os dados sobre uso de hardware dentro da cpuSeries, ram Series e discoSeries, sempre usando .getItemCount()
                // trocar o random pelo uso do Looca
                StopWatch timer = new StopWatch();
                timer.start();
                Memoria ram = looca.getMemoria();
                Processador cpu = looca.getProcessador();

                DiscosGroup rom = looca.getGrupoDeDiscos();
                Conversor conversor = new Conversor();

                List<Volume> volumes = rom.getVolumes();
                Double usoVolume = 0d;
                Long totalVolume = 0l;

                for (Volume volume : volumes) {
                    totalVolume += volume.getTotal();
                    Long totalVolumeAtual = volume.getTotal();
                    Long volumeDisponivel = volume.getDisponivel();
                    usoVolume += Double.valueOf(totalVolumeAtual) - Double.valueOf(volumeDisponivel);
                }
                Double porcentagemVolume = (usoVolume / totalVolume) * 100;

                Long ramUso = ram.getEmUso();
                Long ramTotal = ram.getTotal();
                Double porcentagemRam = (Double.valueOf(ramUso) / Double.valueOf(ramTotal)) * 100;
                try {

                    Double usoCPU = cpu.getUso();

                    Monitorando(usoCPU, porcentagemRam, porcentagemVolume);
                    cpuSeries.add(cpuSeries.getItemCount(), usoCPU);
                    ramSeries.add(ramSeries.getItemCount(), porcentagemRam);
                    discoSeries.add(discoSeries.getItemCount(), porcentagemVolume);
                } catch (Exception ed) {
                    System.out.println(ed);
                }
                timer.stop();
                System.out.println(timer.getTotalTimeSeconds() + " tempo no metodo");
            }
        }).start();
        dataset.addSeries(cpuSeries);
        dataset.addSeries(ramSeries);
        dataset.addSeries(discoSeries);
        return dataset;
    }

    private ChartPanel createPane() {
        final XYSeries series = new XYSeries("Data");
        for (int i = 0; i < random.nextInt(N) + N / 2; i++) {
            series.add(i, 30);
        }
        XYSeriesCollection dataset = gerarDataset();
        new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                series.add(series.getItemCount(), 30);
            }
        }).start();
        JFreeChart chart = ChartFactory.createXYLineChart("Monitoramento", "Leitura",
                "Porcentagem de uso (%)", dataset, PlotOrientation.VERTICAL, true, true, true);

        return new ChartPanel(chart);
    }

    private void initComponents(Usuario usuario) {

        jPanel1 = new javax.swing.JPanel();
        labelSaudacao = new javax.swing.JLabel();
        labelSaudacao.setFont(new Font("Roboto Black", Font.PLAIN, 16));
        qrCodeLabel = new javax.swing.JLabel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        JButton verProcessos = new JButton("Processos");
        verProcessos.setForeground(new Color(255, 255, 255));
        verProcessos.setBackground(new Color(246, 0, 0));
        verProcessos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProcTable processos = new ProcTable();
                processos.criaJanela();
            }
        });
        Integer hora = LocalTime.now().getHour();
        if (hora < 6) {
            labelSaudacao.setText("Boa madrugada, " + usuario.getNome() + "!");
        } else if (hora < 12) {
            labelSaudacao.setText("Bom dia, " + usuario.getNome() + "!");
        } else if (hora < 18) {
            labelSaudacao.setText("Boa tarde, " + usuario.getNome() + "!");
        } else {
            labelSaudacao.setText("Boa noite, " + usuario.getNome() + "!");
        }
        labelSaudacao_1 = new JLabel();
        labelSaudacao_1.setText("Monitore também pelo celular!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(6)
                                                .addComponent(qrCodeLabel, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(71)
                                                                .addComponent(labelSaudacao))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(95)
                                                                .addComponent(verProcessos))))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(38)
                                                .addComponent(labelSaudacao_1, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(265, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20)
                                .addComponent(labelSaudacao)
                                .addGap(28)
                                .addComponent(verProcessos)
                                .addContainerGap(86, Short.MAX_VALUE))
                        .addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(qrCodeLabel, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(labelSaudacao_1))
        );
        jPanel1.setLayout(jPanel1Layout);
        String token = toHex(usuario.getSenha());
        QrCode qr0 = QrCode.encodeText("http://localhost:3333/dashboard/servidores-java.html?idServer=" + fkServidor + "&idUser=" + usuario.getIdUsuario() + "&token=" + token, QrCode.Ecc.LOW);
        BufferedImage img = toImage(qr0, 2, 1); // See QrCodeGeneratorDemo
        String caminho = System.getProperty("java.io.tmpdir") + "/qr-code.png";

        try {
            ImageIO.write(img, "png", new File(caminho));
            qrCodeLabel.setIcon(new javax.swing.ImageIcon(caminho));

        } catch (IOException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        JFreeChart lineChart = ChartFactory.createLineChart("Monitoramento", "Leitura", "Utilização", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        JPanel panel = new JPanel();
        panel.add(createPane());
        panel.setBackground(new Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
                        .addComponent(jPanel1, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 696, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
    }


    /*---- Utilities ----*/
    private static BufferedImage toImage(QrCode qr, int scale, int border) {
        return toImage(qr, scale, border, 0xFFFFFF, 0x000000);
    }

    public String toHex(String arg) {
        return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
    }

    /**
     * Returns a raster image depicting the specified QR Code, with the
     * specified module scale, border modules, and module colors.
     * <p>
     * For example, scale=10 and border=4 means to pad the QR Code with 4 light
     * border modules on all four sides, and use 10&#xD7;10 pixels to represent
     * each module.
     *
     * @param qr the QR Code to render (not {@code null})
     * @param scale the side length (measured in pixels, must be positive) of
     * each module
     * @param border the number of border modules to add, which must be
     * non-negative
     * @param lightColor the color to use for light modules, in 0xRRGGBB format
     * @param darkColor the color to use for dark modules, in 0xRRGGBB format
     * @return a new image representing the QR Code, with padding and scaling
     * @throws NullPointerException if the QR Code is {@code null}
     * @throws IllegalArgumentException if the scale or border is out of range,
     * or if {scale, border, size} cause the image dimensions to exceed
     * Integer.MAX_VALUE
     */
    private static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0) {
            throw new IllegalArgumentException("Value out of range");
        }
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale) {
            throw new IllegalArgumentException("Scale or border too large");
        }

        BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale,
                BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }

    // Helper function to reduce code duplication.
    private static void writePng(BufferedImage img, String filepath) throws IOException {
        ImageIO.write(img, "png", new File(filepath));
    }

    /**
     * Returns a string of SVG code for an image depicting the specified QR
     * Code, with the specified number of border modules. The string always uses
     * Unix newlines (\n), regardless of the platform.
     *
     * @param qr the QR Code to render (not {@code null})
     * @param border the number of border modules to add, which must be
     * non-negative
     * @param lightColor the color to use for light modules, in any format
     * supported by CSS, not {@code null}
     * @param darkColor the color to use for dark modules, in any format
     * supported by CSS, not {@code null}
     * @return a string representing the QR Code as an SVG XML document
     * @throws NullPointerException if any object is {@code null}
     * @throws IllegalArgumentException if the border is negative
     */
    private static String toSvgString(QrCode qr, int border, String lightColor, String darkColor) {
        Objects.requireNonNull(qr);
        Objects.requireNonNull(lightColor);
        Objects.requireNonNull(darkColor);
        if (border < 0) {
            throw new IllegalArgumentException("Border must be non-negative");
        }
        long brd = border;
        StringBuilder sb = new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n").append(
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n")
                .append(String.format(
                        "<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" viewBox=\"0 0 %1$d %1$d\" stroke=\"none\">\n",
                        qr.size + brd * 2))
                .append("\t<rect width=\"100%\" height=\"100%\" fill=\"" + lightColor + "\"/>\n")
                .append("\t<path d=\"");
        for (int y = 0; y < qr.size; y++) {
            for (int x = 0; x < qr.size; x++) {
                if (qr.getModule(x, y)) {
                    if (x != 0 || y != 0) {
                        sb.append(" ");
                    }
                    sb.append(String.format("M%d,%dh1v1h-1z", x + brd, y + brd));
                }
            }
        }
        return sb.append("\" fill=\"" + darkColor + "\"/>\n").append("</svg>\n").toString();
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelSaudacao;
    private javax.swing.JLabel qrCodeLabel;
    private JLabel labelSaudacao_1;
}
