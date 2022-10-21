package view;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.processos.ProcessosGroup;
import java.awt.Color;
import java.awt.Font;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ProcTable extends JFrame {

    JPanel painelFundo;
    JTable tabela;
    JScrollPane barraRolagem;
    private DefaultTableModel modelo = new DefaultTableModel();

  
    public void criaJanela() {
        Looca looca = new Looca();
        ProcessosGroup pg = looca.getGrupoDeProcessos();
        List<Processo> lista = pg.getProcessos().stream().distinct().collect(Collectors.toList());

        Collections.sort(lista, new Comparator<Processo>() {
            @Override
            public int compare(Processo o1, Processo o2) {
                Double uso1 = o1.getUsoCpu();
                Double uso2 = o2.getUsoCpu();
                return uso1.compareTo(uso2);
            }
        });
        painelFundo = new JPanel();
        painelFundo.setLayout(new GridLayout(1, 1));
        tabela = new JTable(modelo);
        Color background = new Color(245,245,245);
        tabela.setBackground(background);
        tabela.getTableHeader().setBackground(new Color(246,0,0));
        tabela.getTableHeader().setForeground(new Color(255,255,255));
        tabela.getTableHeader().setFont(tabela.getFont().deriveFont(Font.BOLD));
        tabela.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.black, 2));
    
        modelo.addColumn("Processo");
        modelo.addColumn("CPU");
        modelo.addColumn("RAM");
        
  
        List<String> nomesProcessos = new ArrayList();
        
        for (int i = lista.size()-1, tamanho = 0; i >= 0; i--) {
        	if(tamanho == 27) {
        		break;
        	}
        	if(!lista.get(i).getNome().equals("Idle") && !nomesProcessos.contains(lista.get(i).getNome())){
            modelo.addRow(new Object[]{lista.get(i).getNome(),
                    String.format("%.2f%%", lista.get(i).getUsoCpu()/10),
                    String.format("%.2f%%", lista.get(i).getUsoMemoria())});
            tamanho++;
        	nomesProcessos.add(lista.get(i).getNome());
        	}
        	

        }

       /* // (memoria em uso / memoria total) * 100 |  problema no uso de ram
        // somaCpu/10 | problema na coleta de uso de cpu
        System.out.println("RAM: " + somaMem);
        System.out.println("CPU: " + somaCpu/10);
        System.out.println(looca.getMemoria());*/
        barraRolagem = new JScrollPane(tabela);
        painelFundo.add(barraRolagem);

        getContentPane().add(painelFundo);
        setSize(350, 492);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("SafeCommerce - Monitoramento");
        ImageIcon img = new ImageIcon(getClass().getResource("/img/logo.png"));
        setIconImage(img.getImage());
    }

    public static void main(String[] args) {
        ProcTable lc = new ProcTable();
        lc.criaJanela();
    }
}
