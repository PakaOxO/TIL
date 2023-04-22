const add = (a: number, b: number) => {
  return a + b;
};

const arrayInsert = <T extends {}>(arr: T[], val: T) => {
  const newArr = [val, ...arr];
  return newArr;
};
