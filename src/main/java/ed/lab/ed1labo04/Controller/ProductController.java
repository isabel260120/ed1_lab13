package ed.lab.ed1labo04.Controller;

import ed.lab.ed1labo04.Entity.ProductEntity;
import ed.lab.ed1labo04.Model.CreateProductRequest;
import ed.lab.ed1labo04.Model.UpdateProductRequest;
import ed.lab.ed1labo04.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductEntity>createProduct(@RequestBody CreateProductRequest createProductRequest){
        try {
            ProductEntity productEntity = productService.createProduct(createProductRequest);
            return ResponseEntity.status(201).body(productEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity>updateProduct(@PathVariable Integer id, @RequestBody UpdateProductRequest updateProductRequest){
        try {
            ProductEntity productEntity = productService.updateProduct(id, updateProductRequest);
            return ResponseEntity.ok(productEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity>getProduct(@PathVariable Integer id) {
        try {
            ProductEntity productEntity = productService.getProductById(id);
            return ResponseEntity.ok(productEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());

    }


}
