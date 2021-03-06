/**
 * NOTE: This class is auto generated by the swagger code generator program (2.4.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package com.stock.api.test.api;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.api.test.model.CreateStockRequest;
import com.stock.api.test.model.ErrorResponse;
import com.stock.api.test.model.StockInfo;
import com.stock.api.test.model.StockListResponse;
import com.stock.api.test.model.UpdateStockPriceRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-05-05T11:51:43.406-03:00")

@Api(value = "Stocks", description = "the Stocks API")
public interface StocksApi {

    Logger log = LoggerFactory.getLogger(StocksApi.class);

    default Optional<ObjectMapper> getObjectMapper() {
        return Optional.empty();
    }

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    default Optional<String> getAcceptHeader() {
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    @ApiOperation(value = "Creates a stock", nickname = "createStock", notes = "Creates a stock", response = StockInfo.class, tags={ "stocks", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successfull response with stock info", response = StockInfo.class),
        @ApiResponse(code = 400, message = "Request is not correct.", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorResponse.class) })
    @RequestMapping(value = "/stocks",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    default CompletableFuture<ResponseEntity<StockInfo>> createStock(@ApiParam(value = "Stock to create"  )  @Valid @RequestBody CreateStockRequest createStockRequest) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return CompletableFuture.completedFuture(new ResponseEntity<>(getObjectMapper().get().readValue("{  \"price\" : 0,  \"stockId\" : { },  \"name\" : { }}", StockInfo.class), HttpStatus.NOT_IMPLEMENTED));
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default StocksApi interface so no example is generated");
        }
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));
    }


    @ApiOperation(value = "Returns Stock of the given ID", nickname = "getStock", notes = "Returns the stock data identified by the id", response = StockInfo.class, tags={ "stocks", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfull response with stock info", response = StockInfo.class),
        @ApiResponse(code = 400, message = "Request is not correct.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "There is no stock for given stockId.", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorResponse.class) })
    @RequestMapping(value = "/stocks/{stockId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default CompletableFuture<ResponseEntity<StockInfo>> getStock(@ApiParam(value = "",required=true) @PathVariable("stockId") String stockId) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return CompletableFuture.completedFuture(new ResponseEntity<>(getObjectMapper().get().readValue("{  \"price\" : 0,  \"stockId\" : { },  \"name\" : { }}", StockInfo.class), HttpStatus.NOT_IMPLEMENTED));
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default StocksApi interface so no example is generated");
        }
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));
    }


    @ApiOperation(value = "Returns list of stocks", nickname = "listStocks", notes = "Returns paginated list of stocks.", response = StockListResponse.class, tags={ "stocks", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfull response with paginated stocks.", response = StockListResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorResponse.class) })
    @RequestMapping(value = "/stocks",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default CompletableFuture<ResponseEntity<StockListResponse>> listStocks(@Min(0)@ApiParam(value = "zero-based page index in list requests.", defaultValue = "0") @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page,@Min(1)@ApiParam(value = "the size of the page to be returned in list requests.", defaultValue = "25") @Valid @RequestParam(value = "size", required = false, defaultValue="25") Integer size) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return CompletableFuture.completedFuture(new ResponseEntity<>(getObjectMapper().get().readValue("\"\"", StockListResponse.class), HttpStatus.NOT_IMPLEMENTED));
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default StocksApi interface so no example is generated");
        }
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));
    }


    @ApiOperation(value = "updates the price of the stock identified by the given id", nickname = "updatePrice", notes = "Updates the price to the one provided in the request for the stock identified by given id.", response = StockInfo.class, tags={ "stocks", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfull response with stock info", response = StockInfo.class),
        @ApiResponse(code = 400, message = "Request is not correct.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "There is no stock for given stockId.", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal Server Error.", response = ErrorResponse.class) })
    @RequestMapping(value = "/stocks/{stockId}",
        produces = { "application/json" }, 
        method = RequestMethod.PATCH)
    default CompletableFuture<ResponseEntity<StockInfo>> updatePrice(@ApiParam(value = "",required=true) @PathVariable("stockId") String stockId,@ApiParam(value = "Price of the stock to update"  )  @Valid @RequestBody UpdateStockPriceRequest updatePriceRequest) {
        if(getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return CompletableFuture.completedFuture(new ResponseEntity<>(getObjectMapper().get().readValue("{  \"price\" : 0,  \"stockId\" : { },  \"name\" : { }}", StockInfo.class), HttpStatus.NOT_IMPLEMENTED));
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default StocksApi interface so no example is generated");
        }
        return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));
    }

}
