



import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;



public class ProdutosDAO {
   
    private conectaDAO conectaDAO; //criando o objeto da classe Conexao
    private Connection conn; //criando a conexao que permitira o CRUD

public ProdutosDAO(){ // criando o construtor da classe 

this.conectaDAO = new conectaDAO();
}
   
   
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){ //metodo para cadastrar clientes 
        
      this.conn = this.conectaDAO.connectDB();
    String sql ="INSERT INTO produtos (nome,valor,status) VALUES"+"(?,?,?)";
    try{
        PreparedStatement stmt = this.conn.prepareStatement(sql);
        stmt.setString(1,produto.getNome());
        stmt.setDouble(2, produto.getValor());   
        stmt.setString(3,produto.getStatus());
        stmt.execute();
        
        JOptionPane.showMessageDialog(null,"Produto inserido com sucesso!!");
        
    }catch(Exception e){
        System.out.println("Erro ao inserir produto : "+e.getMessage());
    }
    this.conectaDAO.desconectar(this.conn);
}
        
     public ProdutosDTO getProduto (int id){ //metodo para buscar produtos a partir do id 
    this.conn = this.conectaDAO.connectDB();
      String sql = "SELECT * FROM produtos WHERE id = ?";
      try{
          PreparedStatement stmt = this.conn.prepareStatement(sql);
          stmt.setInt(1, id);
          ResultSet rs = stmt.executeQuery();
        
          ProdutosDTO produto = new ProdutosDTO();
          rs.next();
          produto.setId(id);
          produto.setNome(rs.getString("nome"));
          produto.setValor(rs.getDouble("valor"));
          produto.setStatus(rs.getString("status"));
          
          return produto;
          
      }catch (Exception e) {
          System.out.println("erro: " + e.getMessage());
          return null;
      }
      finally{
          this.conectaDAO.desconectar(this.conn);
      }  }       
        
        
       
        
    
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        
       this.conn = this.conectaDAO.connectDB();
        listagem.clear();
        String sql = "SELECT * FROM produtos";
        try{
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getDouble("valor"));
                produto.setStatus(rs.getString("status"));
               
                listagem.add(produto);  
            }
                    }catch (Exception e) {
            System.out.println("erro: " + e.getMessage());
            return null;
        }
        finally{
            this.conectaDAO.desconectar(this.conn);
        }      
        return listagem;
    }
    
    
    
        
}

