package ed.lab.ed1labo04.Service;
import ed.lab.ed1labo04.Entity.ProductEntity;
import ed.lab.ed1labo04.Model.CreateProductRequest;
import ed.lab.ed1labo04.Model.UpdateProductRequest;
import ed.lab.ed1labo04.Repository.ProductRepository;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

@Service

public class ProductService {
    private ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity create(CreateProductRequest createProductRequest) {
        if(createProductRequest.getPrice()<=0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(createProductRequest.getName());
        productEntity.setPrice(createProductRequest.getPrice());
        productEntity.setQuantity(0);
        return productRepository.save(productEntity);
    }
    public ProductEntity updateProduct(long id, UpdateProductRequest updateProductRequest) {
        if(updateProductRequest.getPrice()<=0)
            throw new IllegalArgumentException("Price must not be null");

        if(updateProductRequest.getQuantity()<0){
            throw new IllegalArgumentException("Quantity must be greater than 0");

        }
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Product not found"));
        productEntity.setQuantity(updateProductRequest.getQuantity());
        productEntity.setPrice(updateProductRequest.getPrice());
        return productRepository.save(productEntity);
    }
}
