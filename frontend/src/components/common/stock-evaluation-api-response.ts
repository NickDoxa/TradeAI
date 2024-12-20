export type StockEvaluationApiResponse = {
  symbol: string;
  evaluationResponse: string;
  evaluationTime: Date
  evaluationScore: number;
}