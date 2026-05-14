package ed.lab.ed1labo04.Service;
import ed.lab.ed1labo04.Entity.ProductEntity;
import ed.lab.ed1labo04.Model.CreateProductRequest;
import ed.lab.ed1labo04.Model.UpdateProductRequest;
import ed.lab.ed1labo04.Repository.ProductRepository;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductService {
    private ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity createProduct(CreateProductRequest createProductRequest) {
        if(createProductRequest.getPrice()<=0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(createProductRequest.getName());
        productEntity.setPrice(createProductRequest.getPrice());
        productEntity.setQuantity(0);
        return productRepository.save(productEntity);
    }
    public ProductEntity updateProduct(Integer id, UpdateProductRequest updateProductRequest) {
        if (updateProductRequest.getPrice() == null || updateProductRequest.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (updateProductRequest.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must be greater or equal to 0");
        }

        Optional<ProductEntity> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        ProductEntity productEntity = optionalProduct.get();

        productEntity.setPrice(updateProductRequest.getPrice());
        productEntity.setQuantity(updateProductRequest.getQuantity());

        return productRepository.save(productEntity);
    }

    public List<ProductEntity> getAllProducts(){
        return productRepository.findAll();
    }
    public ProductEntity getProductById(Integer id) {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        return optionalProduct.get();
    }
}
