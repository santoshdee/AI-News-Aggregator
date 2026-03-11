function Container({ children }) {
  return (
    <div className="max-w-3xl lg:max-w-4xl xl:max-w-5xl mx-auto px-4 sm:px-6 lg:px-8">
      {children}
    </div>
  );
}

export default Container;
