export const erstelleDatum = (tage) => {
  const d = new Date();
  d.setDate(d.getDate() + tage);
  return d.toISOString().slice(0, 19); // YYYY-MM-DDThh:mm:ss
};