package Practica01_Dropbox;

/**
 *
 * @author Home
 */
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class JTree_prueba implements TreeSelectionListener{
    String path = "C:\\Users\\Home\\Documents\\ESCOM\\5o Semestre\\05 Redes\\Contenido\\Dropbox";
    DefaultMutableTreeNode root;
    DefaultTreeModel model;
    JTree dirTree;
    
    public JTree_prueba(){
        root=new DefaultMutableTreeNode(path);
        model=new DefaultTreeModel(root);
        dirTree=new JTree(model);
    
        dirTree.addTreeSelectionListener(this);
    }
    
    public JTree getTree(){
        return this.dirTree;
    }
    
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath tp=e.getPath();
        Object aux2[]=tp.getPath();
        String aux="";
        for(int i=0;i<aux2.length;i++){
            aux=aux+"/"+((DefaultMutableTreeNode)aux2[i]).toString();
        }
        System.out.println(aux);
        addFolders(aux,this.model,(DefaultMutableTreeNode)tp.getLastPathComponent());
    }
    
    private static void addFolders(String path,DefaultTreeModel m,DefaultMutableTreeNode root){
        File fil=new File(path);
        if(fil!=null){
            String aux[]=fil.list();
            if(aux!=null){
                for(int i=0;i<aux.length;i++){
                    m.insertNodeInto(new DefaultMutableTreeNode(aux[i]), root, i);
                }
            }
        }
    }
    
    private static String[] getDirs(File fil){
        if(fil.exists()){
            return fil.list();
        }else{
            return null;
        }
    }
    
    public static void main(String args[]){
        JFrame frame=new JFrame();
        JTree_prueba dt=new JTree_prueba();
        JScrollPane jsc=new JScrollPane(dt.getTree());
        frame.getContentPane().add(jsc);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

