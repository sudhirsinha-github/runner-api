package mn.swagger.api.service;

import mn.swagger.api.dtos.ProductDTO;

import javax.inject.Singleton;
import java.time.Instant;
import java.util.*;

@Singleton
public class ProductService {

    private Map<String, ProductDTO> productCache = new HashMap<>();

    public ProductDTO create(String id) {
        ProductDTO product = new ProductDTO(UUID.randomUUID().toString(), decorate(id), currentSeconds());
        productCache.put(id, product);
        return product;
    }

    public Optional<ProductDTO> update(String id) {
        Optional<ProductDTO> optproduct = findById(id);
        return optproduct.map( product -> {
            product.setLabel(decorate(id));
            product.setSeconds(currentSeconds());
            return product;
        });
    }

    public Optional<ProductDTO> findById(String id) {
        return Optional.ofNullable(productCache.get(id));
    }

    public List<ProductDTO> findAll() {
        return new ArrayList<>(productCache.values());
    }

    public Optional<ProductDTO> remove(String id) {
        return Optional.ofNullable(productCache.remove(id));
    }

    private String decorate(String text) {
        List<String> decorators = Arrays.asList("***", "---", "|||", "...", "$$$", "!!!");
        int index = new Random().nextInt(decorators.size());
        return decorators.get(index)+" " + text + " "+decorators.get(index);
    }

    private long currentSeconds() {
        return Instant.now().getEpochSecond();
    }
}
