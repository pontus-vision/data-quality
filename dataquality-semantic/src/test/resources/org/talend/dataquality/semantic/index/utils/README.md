Jul. 2015
------------
**Source 1**: [http://data.okfn.org](http://data.okfn.org/data/country-codes), 
licensed by its maintainers under the [Public Domain Dedication and License](http://opendatacommons.org/licenses/pddl/1-0/).<br/>
**File**: *country-codes.csv*.

**Source 2**: Aicha BEN SALEM, PhD from University of Paris 13.<br/>
**16 Files**: *address_cleaned.csv*, *airport_cleaned.csv*, *animal_cleaned.csv*,<br/>
*city_cleaned.csv*, *civility_cleaned.csv*, *continent_cleaned.csv*,<br/>
*days_cleaned.csv*, *department_cleaned.csv*, *drug_cleaned.csv*,<br/>
*jobTitle_cleaned.csv*, *firstname_cleaned.csv*, *medical_tets_cleaned.csv*,<br/>
*months_cleaned.csv*, *gender_cleaned.csv*,*pharmacy_cleaned.csv*,<br/>
*speciality_field_cleaned.csv*.

Oct. 2015
-------------
#### Add 8 new files:

##### Source YAGO:
1. *wordnet_beverages_yago2.csv*, 
2. *wordnet_companies_yago2.csv*,
3. *wordnet_organizations_yago2.csv*,
4. *wordnet_museums_yago2.csv*.

##### Source Wikipedia
1. *us_counties.csv*: [Index of U.S. counties](https://en.wikipedia.org/wiki/Index_of_U.S._counties)
2. *airport-code-wiki.csv*: [List of airports](https://en.wikipedia.org/wiki/List_of_airports)
3. *airport-name-wiki.csv*: [List of airports](https://en.wikipedia.org/wiki/List_of_airports)

##### Others:
1. *industry_GICS_simplified.csv*, 
2. *unitOfMeasurement_cleaned.csv*.

For more details, please check jira issue: [TDQ-10903.](https://jira.talendforge.org/browse/TDQ-10903)

#### Remove 5 files:
1. *drug_cleaned.csv*, 
2. *medical_tets_cleaned.csv*, 
3. *pharmacy_cleaned.csv*, 
4. *speciality_field_cleaned.csv*,
5. *airport_cleaned.csv*,
6. *airport-codes_simplified.csv*: [Airport Codes](http://data.okfn.org/data/core/airport-codes)

#### Review all data source files and correct errors:
1. convert all files to utf 8
2. replace all "\&amp;" by "&"
3. optimize some source files by adding the synonims

Nov. 2015
-------------
#### Add 6 new files:
##### Source INSEE:
1. *comisimp2015.csv* for *FR_COMMUNE* index,
2. *depts2015.csv* for *FR_DEPARTEMENT* index,
3. *reg2015.csv* for *FR_REGION* index.

##### Source wikipedia:
*languages_code_name.csv* for indexes:
 *LANGUAGE*, *LANGUAGE_CODE_ISO2*, *LANGUAGE_CODE_ISO3*

##### Source [statoids.com](http://www.statoids.com/)
1. *ca_province_territory.csv* for the indexes: *CA_PROVINCE_TERRITORY*, *CA_PROVINCE_TERRITORY_CODE*
2. *mx_estado.csv* for the indexes: *MX_ESTADO*, *MX_ESTADO_CODE*

Apr. 2019
-------------
Deleted:
- airport-code-wiki.csv
- airport-name-wiki.csv
- firstname_cleaned.csv
- job_title_cleaned.csv

Add:
- airport-name-code.csv from http://ourairports.com/data/, includes small, medium, and airports, seaplane bases, and closed airports, all having valid IATA code
- Currencies_ActiveCodes.csv active codes currencies from https://en.wikipedia.org/wiki/ISO_4217, double checked with https://www.currency-iso.org/en/home/tables/table-a1.html
- Currencies_Crypto.csv cryptocurrencies from https://en.wikipedia.org/wiki/ISO_4217
- Currencies_Historical.csv historical currency codes from https://en.wikipedia.org/wiki/ISO_4217
- Currencies_NoISO.csv currencies without ISO 4217 currency codes from https://en.wikipedia.org/wiki/ISO_4217
- Currencies_Unofficial.csv unofficial currency codes from https://en.wikipedia.org/wiki/ISO_4217
- firstname_full.csv merge from remaining items from previous file firstname_cleaned.csv 2015 + data from https://github.com/MatthiasWinkelmann/firstname-database (deduplicated + transform "+" occurrences by "-", " " and "")
- industry_FR_ROME.csv  from https://www.data.gouv.fr/en/datasets/repertoire-operationnel-des-metiers-et-des-emplois-rome/
- industry_Profession_Trade_FR.csv from https://www.data.gouv.fr/en/datasets/repertoire-operationnel-des-metiers-et-des-emplois-rome/
- industry_sector_FR_ROME.csv from https://www.data.gouv.fr/en/datasets/repertoire-operationnel-des-metiers-et-des-emplois-rome/
- job_titles_FR_ROME.csv from https://www.data.gouv.fr/en/datasets/repertoire-operationnel-des-metiers-et-des-emplois-rome/
- job_titles_US_Extended.csv (merge of previous file job_title_cleaned.csv, deduplicated + data from https://data.ny.gov/Government-Finance/Title-and-Salary-Listing/t3vp-5tka, KEEP all cases <job_title> 1, <job_title> 2, etc.)
- job_titles_US_Simplified.csv (merge of previous file job_title_cleaned.csv, deduplicated + data from https://data.ny.gov/Government-Finance/Title-and-Salary-Listing/t3vp-5tka, DELETE all cases <job_title> 1, <job_title> 2, etc. but keep one for all)

Update:
- country-codes.csv source replaced by https://www.iso.org/obp/ui/#iso:code:3166:EH and wikipedia for synonyms addition
- us_counties.csv merge previous version 2015 with data from https://en.wikipedia.org/wiki/List_of_United_States_counties_and_county_equivalents + add synonyms for cases "St. -> Saint" and "Ste. -> Sainte"
