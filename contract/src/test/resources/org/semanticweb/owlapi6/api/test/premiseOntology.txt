Prefix(:=<http://example.org/>)
Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)
Ontology(
  Declaration(NamedIndividual(:a))
  Declaration(DataProperty(:dp1))
  Declaration(DataProperty(:dp2))
  Declaration(Class(:A))
  DisjointDataProperties(:dp1 :dp2) 
  DataPropertyAssertion(:dp1 :a "10"^^xsd:integer)
  SubClassOf(:A DataSomeValuesFrom(:dp2 
    DatatypeRestriction(xsd:integer 
      xsd:minInclusive "18"^^xsd:integer 
      xsd:maxInclusive "18"^^xsd:integer)
    )
  )
  ClassAssertion(:A :a)
)