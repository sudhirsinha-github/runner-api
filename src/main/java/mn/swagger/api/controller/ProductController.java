package mn.swagger.api.controller;

import java.util.List;
import javax.validation.constraints.NotBlank;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mn.swagger.api.dtos.ProductDTO;
import mn.swagger.api.service.ProductService;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Controller("/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Post(uri="/products", produces= APPLICATION_JSON)
    @Operation(summary = "Creates a new product object adding a decorated id and the current time",
            description = "Showcase of the creation of a dto"
    )
    @ApiResponse(responseCode = "201", description = "product object correctly created",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type="ProductDTO"))
    )
    @ApiResponse(responseCode = "400", description = "Invalid id Supplied")
    @ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")
    @Tag(name = "create")
    public Single<HttpResponse<ProductDTO>> create(@Body @NotBlank String data ) {
        return Single.just(HttpResponse.created(productService.create(data)));
    }

    @Put(uri="/products/{id}", produces= APPLICATION_JSON)
    @Operation(summary = "Updates an existing product object with a new label and modifying the current time",
            description = "Showcase of the update of a dto"
    )
    @ApiResponse(responseCode = "200", description = "product object correctly updated",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type="ProductDTO"))
    )
    @ApiResponse(responseCode = "404", description = "product not found by using the provided id")
    @ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")
    @Tag(name = "update")
    public Single<HttpResponse<ProductDTO>> update(@Parameter(description="Id to generate a product object") @NotBlank String id) {
        return Single.just(productService.update(id)
                .map(HttpResponse::ok)
                .orElseGet(HttpResponse::notFound)
        );
    }

    @Get(uri="/products/{id}", produces= APPLICATION_JSON)
    @Operation(summary = "Find the product object corresponding to the provided id",
            description = "Showcase of a finder method returning a dto"
    )
    @ApiResponse(responseCode = "200", description = "A product object has been successfully found and returned",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type="ProductDTO"))
    )
    @ApiResponse(responseCode = "404", description = "product not found by using the provided id")
    @ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")
    @Tag(name = "findById")
    public Single<HttpResponse<ProductDTO>> findById(@Parameter(description="id to find a product object") @NotBlank String id) {
        return Single.just(productService.findById(id)
                .map(HttpResponse::ok)
                .orElseGet(HttpResponse::notFound)
        );
    }

    @Get(uri="/products", produces= APPLICATION_JSON)
    @Operation(summary = "Find all the product objects",
            description = "Showcase of a method returning a list of dtos")
    @ApiResponse(responseCode = "200", description = "A product object has been successfully found and returned",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type="ProductDTO"))
    )
    @ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")
    @Tag(name = "findAll")
    public Single<List<ProductDTO>> findAll() {
        return Single.just(productService.findAll());
    }

    @Delete(uri="/products/{id}", produces= APPLICATION_JSON)
    @Operation(summary = "Remove the product object corresponding to the provided id",
            description = "Showcase of a deletion method"
    )
    @ApiResponse(responseCode = "202", description = "product object has been correctly removed")
    @ApiResponse(responseCode = "500", description = "Remote error, server is going nuts")
    @Tag(name = "remove")
    public Single<HttpResponse> remove(@Parameter(description="Id to remove a product object") @NotBlank String id) {
        productService.remove(id);
        return Single.just(HttpResponse.accepted());
    }

}
