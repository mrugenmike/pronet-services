package com.pronet.search.company;

import com.pronet.exceptions.NoContentException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CompanySearchResource {


    private CompanySearchService companySearchService;

    @RequestMapping("companies")
    public CompanyListings searchCompanies(@RequestParam("query") String query,@RequestParam(defaultValue = "0")int skip,@RequestParam(defaultValue = "0")int limit){
        final CompanyListings companyListings = companySearchService.fetchListings(query, skip, limit);
        if(companyListings==null|| companyListings.getCompanyListings().isEmpty()){
            throw new NoContentException("No Companies Listings Found");
        }
        return companyListings;
    }
}
