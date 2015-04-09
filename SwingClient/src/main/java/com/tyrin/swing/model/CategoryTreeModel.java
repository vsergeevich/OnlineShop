package com.tyrin.swing.model;

import com.tyrin.exceptions.DBException;
import com.tyrin.services.ICategoryService;
import com.tyrin.swing.util.Config;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * @author Tyrin V. S. Класс, описывающий модель построения дерева категорий
 */
public class CategoryTreeModel {

    private JTree tree;
    private final ICategoryService catComp;
    private Map<String, String> hashCat = new HashMap<>();

    /**
     * В конструкторе из базы вытягивается все категории
     */
    public CategoryTreeModel() {
        catComp = Config.getServiceFactory().getCategoryService();
        try {
            hashCat = catComp.getCategoryTree();
        } catch (DBException e) {
            JOptionPane.showMessageDialog(null, "<html><table width=400>" + "Ошибка базы данных: " + e.getMessage());
        }
    }

    /**
     * Метод, котороый строит дерево категорий
     *
     * @return JTree
     */
    public JTree getTree() {
        DefaultMutableTreeNode root = getNode("Verhnij", "Категории товаров");
        tree = new JTree(root);
        TreeSelectionModel tsm = tree.getSelectionModel();
        tsm.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        return tree;
    }

    /**
     * Метод, который возвращает список дочкрних категорий, для категории
     * переданной в параметре
     *
     * @param c
     * @return ArrayList<String>
     */
    private ArrayList<String> getChildrenFor(String c) {
        ArrayList<String> children = new ArrayList<>();
        Set<Map.Entry<String, String>> set = hashCat.entrySet();
        for (Map.Entry<String, String> me : set) {
            if (me.getValue().equals(c)) {
                children.add(me.getKey());
            }
        }
        return children;
    }

    /**
     * Метод, который рекурсивно добавляет узлы дерева
     *
     * @param c
     * @param name
     * @return DefaultMutableTreeNode
     */
    private DefaultMutableTreeNode getNode(String c, String name) {
        DefaultMutableTreeNode subRoot = new DefaultMutableTreeNode(name);
        ArrayList<String> childs = getChildrenFor(c);
        for (String child : childs) {
            subRoot.add(getNode(child, child));
        }
        return subRoot;
    }
}
